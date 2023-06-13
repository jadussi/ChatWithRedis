package com.redis.chat.service;


import com.redis.chat.dto.LoginDTO;

public interface LoginService {
	
	// 회원가입 서비스
	void joinService(LoginDTO loginDTO);
}
