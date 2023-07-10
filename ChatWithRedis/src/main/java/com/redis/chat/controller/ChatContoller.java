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
import com.redis.chat.dto.MessageDTO;
import com.redis.chat.dto.RedisChatDTO;
import com.redis.chat.service.ChatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "채팅방 컨트롤러 클래스")
@RequestMapping(value = "/chat", produces = "application/json; charset=utf8")	// 응답시 ResponseEntity 사용할 경우 utf-8 설정을 해주지 않으면 한글이 깨지는 현상 발생
public class ChatContoller {
	
	private final ChatService chatService;	// 채팅방 관련 서비스 객체
	
	@ApiOperation(value = "채팅방 목록 조회 서비스")
	@GetMapping("/list")
	public ArrayList<ChatDTO> selectChatRoomList(){
		return chatService.selectChatRoomList();
		
	}
	
	@ApiOperation(value = "참여중인 채팅방 목록 조회 서비스")
	@GetMapping("/list/{userId}")
	public ResponseEntity<Object> selectUserChatRoomList(ChatDTO chatDTO) {
		ArrayList<ChatDTO> rtn = chatService.selectUserChatRoomList(chatDTO);
		if(null == rtn || rtn.size() == 0) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("참여중인 채팅방이 없습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(rtn);
		}
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
	public @ResponseBody ResponseEntity<String> joinChatRoom(@RequestBody ChatDTO chatDTO, String userNm) {
		try {
			chatService.joinChatRoom(chatDTO, userNm);
			return ResponseEntity.status(HttpStatus.OK).body("채팅방 참여에 성공하였습니다");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@ApiOperation(value = "채팅 메세지 전송 서비스")
	@PostMapping(value = "/message")
	public @ResponseBody ResponseEntity<String> sendChatMessage(@RequestBody MessageDTO messageDTO) {
		try {
			chatService.sendChatMessage(messageDTO);
			return ResponseEntity.status(HttpStatus.OK).body("메세지 전송에 성공하였습니다");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@ApiOperation(value = "채팅방 메세지 조회 서비스")
	@GetMapping(value = "/message")
	public @ResponseBody ResponseEntity<Object> getChatMessage(String roomId, String userId) {
		try {
			ArrayList<RedisChatDTO> rtn = chatService.getChatMessage(roomId, userId);
			return ResponseEntity.status(HttpStatus.OK).body(rtn);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메세지를 가져오는것을 실패하였습니다");
		}
	}
}
