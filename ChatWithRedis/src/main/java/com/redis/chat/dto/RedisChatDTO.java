package com.redis.chat.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "ä�� ����", description = "Redis �� ������ ä�ÿ� ���� ������ ���� Data Transfer Object")
public class RedisChatDTO {
	
	@ApiModelProperty(value = "������ ����ھ��̵�")
	private String userID;
	@ApiModelProperty(value = "����ð�")
	private String nowDate;
	@ApiModelProperty(value = "ä�ó���")
	private String message;
	
	public RedisChatDTO() {
	}
	
	public RedisChatDTO(MessageDTO messageDTO) {
		this.userID = messageDTO.getUserId();
		this.message = messageDTO.getMessage();
	}
	
}
