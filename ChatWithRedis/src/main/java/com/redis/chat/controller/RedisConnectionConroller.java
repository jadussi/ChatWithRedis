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
@Api(tags = "Redis Connection 테스트 컨트롤러 클래스")
public class RedisConnectionConroller {
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	
	@ApiOperation(value = "레디스 연결 테스트")
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String getConnection() {
		
		redisTemplate.opsForValue().append("testConnection", "테스트 커넥션 입니다");
		
		return (String)redisTemplate.opsForValue().get("testConnection");
	}

}
