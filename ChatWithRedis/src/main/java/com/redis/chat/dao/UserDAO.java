package com.redis.chat.dao;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.LoginDTO;

@Mapper
public interface UserDAO {
	
	// ȸ������
	int joinService(LoginDTO loginDTO);
	
	String loginService(LoginDTO loginDTO);
}
