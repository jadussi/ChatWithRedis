package com.redis.chat.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.redis.chat.dao.ChatDAO;
import com.redis.chat.dto.ChatDTO;
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
	public void joinChatRoom(ChatDTO chatDTO) throws Exception {
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
