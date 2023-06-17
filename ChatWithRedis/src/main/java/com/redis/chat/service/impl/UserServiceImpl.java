package com.redis.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
	
	private final UserDAO userDAO;	// ȸ������ DAO ��ü
	
	/** ȸ������ ����
	 *
	 */
	@Override
	public void joinService(LoginDTO loginDTO) {
		log.info("loginDTO : {}",loginDTO);
		userDAO.joinService(loginDTO);
	}
	
	
	/** �α��� ����
	 *
	 */
	@Override
	public String loginService(LoginDTO loginDTO) {
		String userNm = userDAO.loginService(loginDTO);
		return userNm;
	}

}
