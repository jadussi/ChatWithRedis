package com.redis.chat.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redis.chat.dao.ChatDAO;
import com.redis.chat.dto.ChatDTO;
import com.redis.chat.dto.MessageDTO;
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
	private final RedisTemplate<String, Object> redisTemplate;		// redisTemplate
	private final RabbitTemplate rabbitTemplate;					// rabbitTemplate
	private String CHAT_EXCHANGE = "chat.exchange";
	
	SimpleDateFormat  milDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");	// �и���������� ����
	
	String HASH_NAME = "CHAT_READ_POINT";
	
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
			redisTemplate.opsForHash().put(HASH_NAME, hahsKey, redisParam);
			
			if(exCnt >= 2) {
				transactionManager.commit(status);	// �� ���� ���̺��� ������ �Ǿ����� commit
				rabbitTemplate.convertAndSend(CHAT_EXCHANGE, "chat.key."+sortedSetName, message);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw new Exception(null == e.getMessage() ? "ä�ù� ������ �����Ͽ����ϴ�." : e.getMessage());
		}
	}
	
	// �޼��� ����
	@Override
	public void sendChatMessage(MessageDTO messageDTO) {
		String sortedSetName = messageDTO.getRoomId();	// SORTED SET Name
		String hahsKey = messageDTO.getRoomId()+"_"+messageDTO.getUserId();
		Date date = new Date();	// ����ð�
		String nowDate = milDateFormat.format(date);	// SORTED SET score, member, HASH�� value �� ���
		long score = Long.parseLong(nowDate); 
		
		RedisChatDTO redisParam = new RedisChatDTO(messageDTO);
		redisParam.setNowDate(nowDate);				// ����ð� ����
		
		// SORTED SET�� �޼��� ����
		redisTemplate.opsForZSet().add(sortedSetName, redisParam, score);
		// HASH�� ������ ����
		redisTemplate.opsForHash().put(HASH_NAME, hahsKey, redisParam);
		
		rabbitTemplate.convertAndSend(CHAT_EXCHANGE, "chat.key."+sortedSetName, messageDTO.getMessage());
	}
	
	// ä�� �޼��� ��ȸ
	@Override
	public ArrayList<RedisChatDTO> getChatMessage(String roomId, String userId) {
		// HASH ���� ���� ��ġ 
		String hashKey = roomId+"_"+userId;
		long stIdx = redisTemplate.opsForZSet().rank(roomId, redisTemplate.opsForHash().get(HASH_NAME, hashKey));
		if(stIdx < 0) stIdx = 0;	// ���� index�� 0���� ������ 0���� set
		long endIdx = stIdx+2;		// 3���� �����ֱ�
		
		Set<TypedTuple<Object>> rtnSet = redisTemplate.opsForZSet().rangeWithScores(roomId, stIdx, endIdx);
		
		RedisChatDTO updateChatDTO = new RedisChatDTO();
		ArrayList<RedisChatDTO> rtnArr = new ArrayList<>();
		// ������ ���� ������ ��ġ�� HASH update
		for(TypedTuple tt : rtnSet) {
			updateChatDTO = (RedisChatDTO) tt.getValue();
			rtnArr.add(updateChatDTO);
		}
		redisTemplate.opsForHash().put(HASH_NAME, hashKey, updateChatDTO);	// HASH update �Ͽ� ������ ���� ��ġ ����
		return rtnArr;
	}
}
