package com.redis.chat.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.redis.chat.dto.ChatDTO;
import com.redis.chat.service.ChatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "채팅방 컨트롤러 클래스")
@RequestMapping(value = "/chat" , produces = "application/json; charset=utf8")	// 응답시 ResponseEntity 사용할 경우 utf-8 설정을 해주지 않으면 한글이 깨지는 현상 발생
public class ChatContoller {
	
	private final ChatService chatService;	// 채팅방 관련 서비스 객체
	
	@ApiOperation(value = "채팅방 목록 조회 서비스")
	@GetMapping("/list")
	public ArrayList<ChatDTO> selectChatRoomList(){
		return chatService.selectChatRoomList();
		
	}
	
	@ApiOperation(value = "채팅방 개설 서비스")
	@PostMapping(value = "/room")
	public @ResponseBody ResponseEntity<String> makeChatRoom(@RequestBody ChatDTO chatDTO) {
		try {
			chatService.makeChatRoom(chatDTO);
			return ResponseEntity.status(HttpStatus.OK).body("채팅방 개설에 성공하였습니다");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@ApiOperation(value = "채팅방 참여 서비스")
	@PostMapping(value = "/room/parti")
	public @ResponseBody ResponseEntity<String> joinChatRoom(@RequestBody ChatDTO chatDTO) {
		try {
			chatService.joinChatRoom(chatDTO);
			// TODO  Redis, Rabbit MQ 구현 
			return ResponseEntity.status(HttpStatus.OK).body("채팅방 참여에 성공하였습니다");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
