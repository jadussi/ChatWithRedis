package com.redis.chat.service;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.redis.chat.dto.ChatDTO;
import com.redis.chat.dto.MessageDTO;
import com.redis.chat.dto.RedisChatDTO;

public interface ChatService {
	// ä�ù� ��� ��ȸ
	ArrayList<ChatDTO> selectChatRoomList();
	
	// �������� ä�ù� ��� ��ȸ
	ArrayList<ChatDTO> selectUserChatRoomList(ChatDTO chatDTO);
	
	// ä�ù� ����
	void makeChatRoom(ChatDTO chatDTO) throws Exception;
	
	// ä�ù� ������ �߰�
	void joinChatRoom(ChatDTO chatDTO, String userNm) throws Exception;
	
	// ä�� �޼��� ����
	void sendChatMessage(MessageDTO messageDTO);
	
	// ä�� �޼��� ��ȸ
	ArrayList<RedisChatDTO> getChatMessage(String roomId, String userId);
}
