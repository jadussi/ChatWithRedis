package com.redis.chat.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.ChatDTO;

@Mapper
public interface ChatDAO {
	
	// ä�ù� ��� ��ȸ
	ArrayList<ChatDTO> selectChatRoomList();
	
	// ä�ù� roomId �����
	String makeRoomId();
	
	// ä�ù� ����
	int makeChatRoom(ChatDTO chatDTO) throws Exception;
	
	// ä�ù� ������ �߰�
	int insertRommParti(ChatDTO chatDTO) throws Exception;
}
