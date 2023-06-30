package com.redis.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.chat.dto.LoginDTO;
import com.redis.chat.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "ȸ������ ��Ʈ�ѷ� Ŭ����")
@RequestMapping("/user")
public class UserController {
	
	// ȸ������ ���� ��ü
	private final UserService userService;
	
	@ApiOperation(value = "ȸ������ ����")
	@PostMapping("/join")
	public String joinService(@RequestBody LoginDTO loginDTO) throws Exception {
		userService.joinService(loginDTO);
		log.info("ȸ������ �Ϸ�");
		return null;
	}
	
	@ApiOperation(value = "�α��� ����")
	@GetMapping("/login/{userId}/{userPw}")
	public String loginService(LoginDTO loginDTO) {
		return userService.loginService(loginDTO);
	}
}
