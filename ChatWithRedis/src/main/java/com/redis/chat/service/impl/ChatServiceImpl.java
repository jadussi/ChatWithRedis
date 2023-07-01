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
	
	private final ChatDAO chatDAO;	// ä�ù� ���� DAO ��ü
	
	private final PlatformTransactionManager transactionManager;	// Ʈ����� ���� ��ü
	private final RedisTemplate<String, Object> redisTemplate;	// redisTemplate
	
	SimpleDateFormat  milDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");	// �и���������� ����
	
	
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
	 * �������� ä�ù� ��� ��ȸ
	 */
	
	@Override
	public ArrayList<ChatDTO> selectUserChatRoomList(ChatDTO chatDTO) {
		ArrayList<ChatDTO> rtn = chatDAO.selectUserChatRoomList(chatDTO);
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

	/**
	 * ä�ù� ���� ����
	 */
	@Override
	public void joinChatRoom(ChatDTO chatDTO, String userNm) throws Exception {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		int exCnt = 0;	// insert, update �Ǽ�
		try {
			// ä�ù� �����ϱ� �� �����ϰ��� �ϴ� ä�ù� ä���������� �ٽ� Ȯ�� �� ä�ù� ������ �� ���� �� ä�ù� �ο� ���̺� insert
			ChatDTO checkDTO =  chatDAO.selectChatRoomStatus(chatDTO);	// �����ϰ��� �ϴ� ä�ù� �������� ���� Ȯ��
			
			if(checkDTO.getPartiYn().equals("N")) {	// ä�ù� ���� ���ɿ��ΰ� N�϶� ���� �Ұ�
				throw new Exception("���� �� �� ���� ä�ù� �Դϴ�.");
			}
			if(checkDTO.getPartiYn().equals("Y") &&
					(Integer.parseInt(checkDTO.getPrePartiNum())+1 == Integer.parseInt(checkDTO.getPartiNum()))) {
				checkDTO.setPartiYn("N");
			}
			checkDTO.setUserId(chatDTO.getUserId());	// ����� Id set
			
			exCnt += chatDAO.updateChatRoomParti(checkDTO);	// ä�ù� ������ �� ����
			exCnt += chatDAO.insertRommParti(checkDTO);		// ä�ù� ������ �߰�
			
			// SORTED SET : ��ȭ������� ��°�
			// HASH : ������ ���� ��ġ�� ǥ���ϴ°�
			
			// SORTED SET Name : roomId
			// SORTED SET socre : ���� �ð�
			// SORTED SET member : {ä�ó���, ���� ����ھ��̵�, ä�� �����ð� ���}
			// ä�ó��� : ������������ ä�ÿ� �����ϼ̽��ϴ�.(ȭ�鿡�� ����� �̸� �޾ƿ;���)
			
			// HASH Name : CHAT_READ_POINT
			// HASH key : ä�ù�Id_����ھ��̵�
			// HASH value : {ä�ó���, ���� ����ھ��̵�, ä�� �����ð� ���}
			
			String sortedSetName = chatDTO.getRoomId();	// SORTED SET Name
			String hashName = "CHAT_READ_POINT";		// HASH Name
			String hahsKey = chatDTO.getRoomId()+"_"+chatDTO.getUserId();
			Date date = new Date();	// ����ð�
			String nowDate = milDateFormat.format(date);	// SORTED SET score, member, HASH�� value �� ���
			long score = Long.parseLong(nowDate); 
			String message = userNm+"���� ���� �ϼ̽��ϴ�.";
			
			// Redis�� ������ �Ķ���� ����
			RedisChatDTO redisParam = new RedisChatDTO();
			redisParam.setUserID(chatDTO.getUserId());	// ����� ���̵� ����
			redisParam.setNowDate(nowDate);				// ����ð� ����
			redisParam.setMessage(message);
			
			// SORTED SET�� �޼��� ����
			redisTemplate.opsForZSet().add(sortedSetName, redisParam, score);
			// HASH�� ������ ����
			redisTemplate.opsForHash().put(hashName, hahsKey, redisParam);
			
			if(exCnt >= 2) {
				transactionManager.commit(status);	// �� ���� ���̺��� ������ �Ǿ����� commit
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(null == e.getMessage() ? "ä�ù� ������ �����Ͽ����ϴ�." : e.getMessage());
		}
	}
	

}
