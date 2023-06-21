package com.redis.chat.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.ChatDTO;

@Mapper
public interface ChatDAO {
	
	// ä�ù� ��� ��ȸ
	ArrayList<ChatDTO> selectChatRoomList();
	
	// ä�ù� ����
	void makeChatRoom(ChatDTO chatDTO) throws Exception;
}
