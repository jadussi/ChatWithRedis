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
@Api(tags = "Redis Connection 테스트 컨트롤러 클래스")
@RequestMapping("/redis")
public class RedisConnectionConroller {
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	
	@ApiOperation(value = "레디스 연결 테스트")
	@GetMapping("/test/{param}")
	public String getConnection(@ApiParam(value = "String 자료구조 파라메터") @RequestBody String param) {
		
		redisTemplate.opsForValue().append("testConnection", param);
		
		return (String)redisTemplate.opsForValue().get("testConnection");
	}

}
