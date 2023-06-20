package com.redis.chat.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.redis.chat.dao.ChatDAO;
import com.redis.chat.dto.ChatDTO;
import com.redis.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

/**
 * @author jadussi
 *
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
	
	private final ChatDAO chatDAO;	// 채팅방 관련 DAO 객체
	
	/**
	 * 채팅방목록조회
	 */
	@Override
	public ArrayList<ChatDTO> selectChatRoomList() {
		ArrayList<ChatDTO> rtn = chatDAO.selectChatRoomList();
		if(null == rtn || rtn.size() == 0) {
			rtn = new ArrayList<>();
		}
		return rtn;
	}

}
