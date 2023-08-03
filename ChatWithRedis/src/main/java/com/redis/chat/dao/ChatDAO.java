package com.redis.chat.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.ChatDTO;

@Mapper
public interface ChatDAO {
	
	// 채팅방 목록 조회
	ArrayList<ChatDTO> selectChatRoomList();
	
	// 참여중인 채팅방 목록 조회
	ArrayList<ChatDTO> selectUserChatRoomList(ChatDTO chatDTO);
	
	// 채팅방 roomId 만들기
	String makeRoomId();
	
	// 채팅방 개설
	int makeChatRoom(ChatDTO chatDTO) throws Exception;
	
	// 채팅방 참여자 추가
	int insertRommParti(ChatDTO chatDTO) throws Exception;
	
	// 채팅방 참여가능 여부 확인
	ChatDTO selectChatRoomStatus(ChatDTO chatDTO);
	
	// 채팅방 참여자 수 변경
	int updateChatRoomParti(ChatDTO chatDTO) throws Exception;
	
}
