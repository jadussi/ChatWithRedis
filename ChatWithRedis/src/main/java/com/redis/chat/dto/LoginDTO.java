package com.redis.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
	
	private String userId;
	private String userPw;
	private String userNm;
}