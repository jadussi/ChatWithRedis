package com.redis.chat.service;

import java.util.ArrayList;

import com.redis.chat.dto.ChatDTO;

public interface ChatService {
	// ä�ù� ��� ��ȸ
	ArrayList<ChatDTO> selectChatRoomList();
}
