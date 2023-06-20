package com.redis.chat.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.chat.dto.ChatDTO;
import com.redis.chat.service.ChatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "ä�ù� ��Ʈ�ѷ� Ŭ����")
@RequestMapping("/chat")
public class ChatContoller {
	
	private final ChatService chatService;	// ä�ù� ���� ���� ��ü
	
	@ApiOperation(value = "ä�ù� ��� ��ȸ ����")
	@GetMapping("/list")
	public ArrayList<ChatDTO> selectChatRoomList(){
		return chatService.selectChatRoomList();
		
	}

}
