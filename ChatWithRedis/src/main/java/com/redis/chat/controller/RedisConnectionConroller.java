package com.redis.chat.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Api(tags = "Redis Connection �׽�Ʈ ��Ʈ�ѷ� Ŭ����")
@RequestMapping("/redis")
public class RedisConnectionConroller {
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	
	@ApiOperation(value = "���� ���� �׽�Ʈ")
	@GetMapping("/test/{param}")
	public String getConnection(@ApiParam(value = "String �ڷᱸ�� �Ķ����") @RequestBody String param) {
		
		redisTemplate.opsForValue().append("testConnection", param);
		
		return (String)redisTemplate.opsForValue().get("testConnection");
	}

}
