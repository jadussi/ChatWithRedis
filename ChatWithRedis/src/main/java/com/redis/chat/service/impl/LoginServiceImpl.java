package com.redis.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.redis.chat.dao.LoginDAO;
import com.redis.chat.dto.LoginDTO;
import com.redis.chat.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jadussi
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
	
	private final LoginDAO loginDAO;	// 회원관련 DAO 객체
	
	/** 회원가입 서비스
	 *
	 */
	@Override
	public void joinService(LoginDTO loginDTO) {
		log.info("loginDTO : {}",loginDTO);
		loginDAO.joinService(loginDTO);
	}

}
