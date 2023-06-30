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
@Api(tags = "회원관련 컨트롤러 클래스")
@RequestMapping("/user")
public class UserController {
	
	// 회원관련 서비스 객체
	private final UserService userService;
	
	@ApiOperation(value = "회원가입 서비스")
	@PostMapping("/join")
	public String joinService(@RequestBody LoginDTO loginDTO) throws Exception {
		userService.joinService(loginDTO);
		log.info("회원가입 완료");
		return null;
	}
	
	@ApiOperation(value = "로그인 서비스")
	@GetMapping("/login/{userId}/{userPw}")
	public String loginService(LoginDTO loginDTO) {
		return userService.loginService(loginDTO);
	}
}
