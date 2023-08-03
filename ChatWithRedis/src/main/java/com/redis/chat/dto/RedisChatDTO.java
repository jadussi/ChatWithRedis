package com.redis.chat.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "채팅 내용", description = "Redis 에 저장할 채팅에 대한 내용을 담응 Data Transfer Object")
public class RedisChatDTO {
	
	@ApiModelProperty(value = "보내는 사용자아이디")
	private String userID;
	@ApiModelProperty(value = "현재시간")
	private String nowDate;
	@ApiModelProperty(value = "채팅내용")
	private String message;
	
	public RedisChatDTO() {
	}
	
	public RedisChatDTO(MessageDTO messageDTO) {
		this.userID = messageDTO.getUserId();
		this.message = messageDTO.getMessage();
	}
	
}
