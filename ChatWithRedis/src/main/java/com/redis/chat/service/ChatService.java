package com.redis.chat.service;

import java.util.ArrayList;

import com.redis.chat.dto.ChatDTO;

public interface ChatService {
	// 채팅방 목록 조회
	ArrayList<ChatDTO> selectChatRoomList();
	
	// 채팅방 개설
	void makeChatRoom(ChatDTO chatDTO) throws Exception;
}
