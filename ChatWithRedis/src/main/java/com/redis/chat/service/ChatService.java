package com.redis.chat.service;

import java.util.ArrayList;

import com.redis.chat.dto.ChatDTO;

public interface ChatService {
	// ä�ù� ��� ��ȸ
	ArrayList<ChatDTO> selectChatRoomList();
	
	// �������� ä�ù� ��� ��ȸ
	ArrayList<ChatDTO> selectUserChatRoomList(ChatDTO chatDTO);
	
	// ä�ù� ����
	void makeChatRoom(ChatDTO chatDTO) throws Exception;
	
	// ä�ù� ������ �߰�
	void joinChatRoom(ChatDTO chatDTO, String userNm) throws Exception;

}
