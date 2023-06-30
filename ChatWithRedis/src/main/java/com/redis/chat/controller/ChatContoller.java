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
@Api(tags = "ä�ù� ��Ʈ�ѷ� Ŭ����")
@RequestMapping(value = "/chat" , produces = "application/json; charset=utf8")	// ����� ResponseEntity ����� ��� utf-8 ������ ������ ������ �ѱ��� ������ ���� �߻�
public class ChatContoller {
	
	private final ChatService chatService;	// ä�ù� ���� ���� ��ü
	
	@ApiOperation(value = "ä�ù� ��� ��ȸ ����")
	@GetMapping("/list")
	public ArrayList<ChatDTO> selectChatRoomList(){
		return chatService.selectChatRoomList();
		
	}
	
	@ApiOperation(value = "ä�ù� ���� ����")
	@PostMapping(value = "/room")
	public @ResponseBody ResponseEntity<String> makeChatRoom(@RequestBody ChatDTO chatDTO) {
		try {
			chatService.makeChatRoom(chatDTO);
			return ResponseEntity.status(HttpStatus.OK).body("ä�ù� ������ �����Ͽ����ϴ�");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@ApiOperation(value = "ä�ù� ���� ����")
	@PostMapping(value = "/room/parti")
	public @ResponseBody ResponseEntity<String> joinChatRoom(@RequestBody ChatDTO chatDTO) {
		try {
			chatService.joinChatRoom(chatDTO);
			// TODO  Redis, Rabbit MQ ���� 
			return ResponseEntity.status(HttpStatus.OK).body("ä�ù� ������ �����Ͽ����ϴ�");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
