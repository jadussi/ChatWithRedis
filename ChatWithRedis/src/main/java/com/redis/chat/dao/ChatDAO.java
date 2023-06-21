package com.redis.chat.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.ChatDTO;

@Mapper
public interface ChatDAO {
	
	// 채팅방 목록 조회
	ArrayList<ChatDTO> selectChatRoomList();
	
	// 채팅방 개설
	void makeChatRoom(ChatDTO chatDTO) throws Exception;
}
