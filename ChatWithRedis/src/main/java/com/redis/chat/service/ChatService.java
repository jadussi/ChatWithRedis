package com.redis.chat.service;

import java.util.ArrayList;

import com.redis.chat.dto.ChatDTO;

public interface ChatService {
	// 채팅방 목록 조회
	ArrayList<ChatDTO> selectChatRoomList();
	
	// 참여중인 채팅방 목록 조회
	ArrayList<ChatDTO> selectUserChatRoomList(ChatDTO chatDTO);
	
	// 채팅방 개설
	void makeChatRoom(ChatDTO chatDTO) throws Exception;
	
	// 채팅방 참여자 추가
	void joinChatRoom(ChatDTO chatDTO, String userNm) throws Exception;

}
