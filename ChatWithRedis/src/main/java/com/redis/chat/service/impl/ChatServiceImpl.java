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

}
