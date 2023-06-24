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
	
	private final ChatDAO chatDAO;	// ä�ù� ���� DAO ��ü
	
	private final PlatformTransactionManager transactionManager;	// Ʈ����� ���� ��ü
	
	
	/**
	 * ä�ù�����ȸ
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
	 * ä�ù� ���� ����
	 */
	@Override
	public void makeChatRoom(ChatDTO chatDTO) throws Exception {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());	// Ʈ����� ����
		int excnt = 0;	//insert �Ǽ�
		try {
			String roomId = chatDAO.makeRoomId();	// ä�ù� ���̵� ���� ä�ù� ���� �� ���� ���(2���� ���̺��� ���)
			chatDTO.setRoomId(roomId);
			excnt += chatDAO.makeChatRoom(chatDTO);
			excnt += chatDAO.insertRommParti(chatDTO);
			if(excnt >= 2) {
				transactionManager.commit(status);	// ä�ù� ���� �� commit
			} else {
				throw new Exception();	// �ΰ��� ���̺� insert ������ �ΰ��� ���� �� ������ �ʾ��� �� ���� ��Ȳ�� �߻� �Ѱ����� ����
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception("ä�ù� ������ �����Ͽ����ϴ�");	// ä�ù� ���� ���� �� rollback
		}
		
	}

}
