package com.redis.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "채팅 메세지 전송에 필요한 Data Transfer Object")
public class MessageDTO {
	
	@ApiModelProperty(value = "채팅방 아이디")
	private String roomId;
	@ApiModelProperty(value = "메세지")
	private String message;
	@ApiModelProperty(value = "보내는 사용자 아이디")
	private String userId;
	
}
