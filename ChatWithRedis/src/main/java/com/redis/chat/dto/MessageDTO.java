package com.redis.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "ä�� �޼��� ���ۿ� �ʿ��� Data Transfer Object")
public class MessageDTO {
	
	@ApiModelProperty(value = "ä�ù� ���̵�")
	private String roomId;
	@ApiModelProperty(value = "�޼���")
	private String message;
	@ApiModelProperty(value = "������ ����� ���̵�")
	private String userId;
	
}
