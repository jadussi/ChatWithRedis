package com.redis.chat.dao;

import org.apache.ibatis.annotations.Mapper;

import com.redis.chat.dto.LoginDTO;

@Mapper
public interface LoginDAO {
	
	// ȸ������
	int joinService(LoginDTO loginDTO);
}
