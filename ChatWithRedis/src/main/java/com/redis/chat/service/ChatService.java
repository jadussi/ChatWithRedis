package com.redis.chat.service;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.redis.chat.dto.ChatDTO;
import com.redis.chat.dto.MessageDTO;
import com.redis.chat.dto.RedisChatDTO;

public interface ChatService {
	// 채팅방 목록 조회
	ArrayList<ChatDTO> selectChatRoomList();
	
	// 참여중인 채팅방 목록 조회
	ArrayList<ChatDTO> selectUserChatRoomList(ChatDTO chatDTO);
	
	// 채팅방 개설
	void makeChatRoom(ChatDTO chatDTO) throws Exception;
	
	// 채팅방 참여자 추가
	void joinChatRoom(ChatDTO chatDTO, String userNm) throws Exception;
	
	// 채팅 메세지 전송
	void sendChatMessage(MessageDTO messageDTO);
	
	// 채팅 메세지 조회
	ArrayList<RedisChatDTO> getChatMessage(String roomId, String userId);
}
