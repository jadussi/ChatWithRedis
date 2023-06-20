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
@Api(tags = "채팅방 컨트롤러 클래스")
@RequestMapping("/chat")
public class ChatContoller {
	
	private final ChatService chatService;	// 채팅방 관련 서비스 객체
	
	@ApiOperation(value = "채팅방 목록 조회 서비스")
	@GetMapping("/list")
	public ArrayList<ChatDTO> selectChatRoomList(){
		return chatService.selectChatRoomList();
		
	}

}
