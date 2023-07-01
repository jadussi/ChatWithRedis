package com.redis.chat.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.redis.chat.dao.ChatDAO;
import com.redis.chat.dto.ChatDTO;
import com.redis.chat.dto.RedisChatDTO;
import com.redis.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

/**
 * @author jadussi
 *
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	
	private final ChatDAO chatDAO;	// 채팅방 관련 DAO 객체
	
	private final PlatformTransactionManager transactionManager;	// 트랜잭션 관리 객체
	private final RedisTemplate<String, Object> redisTemplate;	// redisTemplate
	
	SimpleDateFormat  milDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");	// 밀리세컨드까지 포함
	
	
	/**
	 * 채팅방목록조회
	 */
	@Override
	public ArrayList<ChatDTO> selectChatRoomList() {
		ArrayList<ChatDTO> rtn = chatDAO.selectChatRoomList();
		if(null == rtn || rtn.size() == 0) {
			rtn = new ArrayList<>();
		}
		return rtn;
	}

	/**
	 * 참여중인 채팅방 목록 조회
	 */
	
	@Override
	public ArrayList<ChatDTO> selectUserChatRoomList(ChatDTO chatDTO) {
		ArrayList<ChatDTO> rtn = chatDAO.selectUserChatRoomList(chatDTO);
		return rtn;
	}
	
	/**
	 * 채팅방 개설 서비스
	 */
	@Override
	public void makeChatRoom(ChatDTO chatDTO) throws Exception {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());	// 트랜잭션 성공
		int excnt = 0;	//insert 건수
		try {
			String roomId = chatDAO.makeRoomId();	// 채팅방 아이디를 만들어서 채팅방 개설 을 위해 사용(2개의 테이블에서 사용)
			chatDTO.setRoomId(roomId);
			excnt += chatDAO.makeChatRoom(chatDTO);
			excnt += chatDAO.insertRommParti(chatDTO);
			if(excnt >= 2) {
				transactionManager.commit(status);	// 채팅방 개설 시 commit
			} else {
				throw new Exception();	// 두개의 테이블에 insert 하지만 두개가 전부 다 변하지 않았을 때 예외 상황이 발생 한것으로 간주
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception("채팅방 개설에 실패하였습니다");	// 채팅방 개설 실패 시 rollback
		}
		
	}

	/**
	 * 채팅방 참여 서비스
	 */
	@Override
	public void joinChatRoom(ChatDTO chatDTO, String userNm) throws Exception {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		int exCnt = 0;	// insert, update 건수
		try {
			// 채팅방 참여하기 전 참여하고자 하는 채팅방 채팅참여여부 다시 확인 후 채팅방 참여자 수 변경 및 채팅방 인원 테이블 insert
			ChatDTO checkDTO =  chatDAO.selectChatRoomStatus(chatDTO);	// 참여하고자 하는 채팅방 참여여부 상태 확인
			
			if(checkDTO.getPartiYn().equals("N")) {	// 채팅방 참여 가능여부가 N일때 참여 불가
				throw new Exception("참여 할 수 없는 채팅방 입니다.");
			}
			if(checkDTO.getPartiYn().equals("Y") &&
					(Integer.parseInt(checkDTO.getPrePartiNum())+1 == Integer.parseInt(checkDTO.getPartiNum()))) {
				checkDTO.setPartiYn("N");
			}
			checkDTO.setUserId(chatDTO.getUserId());	// 사용자 Id set
			
			exCnt += chatDAO.updateChatRoomParti(checkDTO);	// 채팅방 참여자 수 변경
			exCnt += chatDAO.insertRommParti(checkDTO);		// 채팅방 참여자 추가
			
			// SORTED SET : 대화내용들을 담는곳
			// HASH : 마지막 읽은 위치를 표시하는곳
			
			// SORTED SET Name : roomId
			// SORTED SET socre : 참여 시각
			// SORTED SET member : {채팅내용, 보낸 사용자아이디, 채팅 보낸시각 등등}
			// 채팅내용 : 누구누구님이 채팅에 참여하셨습니다.(화면에서 사용자 이름 받아와야함)
			
			// HASH Name : CHAT_READ_POINT
			// HASH key : 채팅방Id_사용자아이디
			// HASH value : {채팅내용, 보낸 사용자아이디, 채팅 보낸시각 등등}
			
			String sortedSetName = chatDTO.getRoomId();	// SORTED SET Name
			String hashName = "CHAT_READ_POINT";		// HASH Name
			String hahsKey = chatDTO.getRoomId()+"_"+chatDTO.getUserId();
			Date date = new Date();	// 현재시각
			String nowDate = milDateFormat.format(date);	// SORTED SET score, member, HASH의 value 에 사용
			long score = Long.parseLong(nowDate); 
			String message = userNm+"님이 참여 하셨습니다.";
			
			// Redis에 전달할 파라메터 설정
			RedisChatDTO redisParam = new RedisChatDTO();
			redisParam.setUserID(chatDTO.getUserId());	// 사용자 아이디 설정
			redisParam.setNowDate(nowDate);				// 현재시간 설정
			redisParam.setMessage(message);
			
			// SORTED SET에 메세지 전송
			redisTemplate.opsForZSet().add(sortedSetName, redisParam, score);
			// HASH에 데이터 전송
			redisTemplate.opsForHash().put(hashName, hahsKey, redisParam);
			
			if(exCnt >= 2) {
				transactionManager.commit(status);	// 두 개의 테이블이 변경이 되었을때 commit
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(null == e.getMessage() ? "채팅방 참여에 실패하였습니다." : e.getMessage());
		}
	}
	

}
