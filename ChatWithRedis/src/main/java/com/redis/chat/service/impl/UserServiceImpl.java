package com.redis.chat.service.impl;

import org.springframework.stereotype.Service;

import com.redis.chat.dao.UserDAO;
import com.redis.chat.dto.LoginDTO;
import com.redis.chat.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jadussi
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserDAO userDAO;	// 회원관련 DAO 객체
	
	/** 회원가입 서비스
	 *
	 */
	@Override
	public void joinService(LoginDTO loginDTO) {
		log.info("loginDTO : {}",loginDTO);
		userDAO.joinService(loginDTO);
	}
	
	
	/** 로그인 서비스
	 *
	 */
	@Override
	public String loginService(LoginDTO loginDTO) {
		String userNm = userDAO.loginService(loginDTO);
		return userNm;
	}

}
