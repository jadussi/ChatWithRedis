package com.redis.chat.service;


import com.redis.chat.dto.LoginDTO;

public interface UserService {
	
	// ȸ������ ����
	void joinService(LoginDTO loginDTO);
	
	// �α��� ����
	String loginService(LoginDTO loginDTO);
}
