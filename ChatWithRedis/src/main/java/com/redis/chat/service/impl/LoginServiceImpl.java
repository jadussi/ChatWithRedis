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
	
	private final LoginDAO loginDAO;	// ȸ������ DAO ��ü
	
	/** ȸ������ ����
	 *
	 */
	@Override
	public void joinService(LoginDTO loginDTO) {
		log.info("loginDTO : {}",loginDTO);
		loginDAO.joinService(loginDTO);
	}

}
