package com.redis.chat.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Api(tags = "Redis Connection �׽�Ʈ ��Ʈ�ѷ� Ŭ����")
public class RedisConnectionConroller {
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	
	@ApiOperation(value = "���� ���� �׽�Ʈ")
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String getConnection() {
		
		redisTemplate.opsForValue().append("testConnection", "�׽�Ʈ Ŀ�ؼ� �Դϴ�");
		
		return (String)redisTemplate.opsForValue().get("testConnection");
	}

}
