package com.redis.chat.dao;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.LoginDTO;

@Mapper
public interface UserDAO {
	
	// 회원가입
	int joinService(LoginDTO loginDTO);
	
	String loginService(LoginDTO loginDTO);
}
