package com.redis.chat.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redis.chat.dto.LoginDTO;
import com.redis.chat.service.LoginService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {
	
	// 회원관련 서비스 객체
	private final LoginService loginService;
	
	@ApiOperation(value = "회원가입 서비스")
	@RequestMapping(value = "user", method = RequestMethod.POST)
	public String joinService(@RequestBody LoginDTO loginDTO) throws Exception {
		loginService.joinService(loginDTO);
		log.info("회원가입 완료");
		return null;
	}
	
}
