package com.redis.chat.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.ChatDTO;

@Mapper
public interface ChatDAO {
	
	// 채팅방 목록 조회
	ArrayList<ChatDTO> selectChatRoomList();
	
	// 채팅방 roomId 만들기
	String makeRoomId();
	
	// 채팅방 개설
	int makeChatRoom(ChatDTO chatDTO) throws Exception;
	
	// 채팅방 참여자 추가
	int insertRommParti(ChatDTO chatDTO) throws Exception;
}
