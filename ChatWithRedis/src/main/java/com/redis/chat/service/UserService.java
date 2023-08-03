package com.redis.chat.service;


import com.redis.chat.dto.LoginDTO;

public interface UserService {
	
	// 회원가입 서비스
	void joinService(LoginDTO loginDTO);
	
	// 로그인 서비스
	String loginService(LoginDTO loginDTO);
}
