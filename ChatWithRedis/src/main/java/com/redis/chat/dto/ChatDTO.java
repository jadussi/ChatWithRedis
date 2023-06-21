package com.redis.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "ä�ù� ����", description = "ä�ù���̵�, ä�ù� �̸�, ä�ù� Ÿ���� ���� Data Transfer Object")
public class ChatDTO {
	@ApiModelProperty(value = "ä�ù� ���̵�")
	private String roomId;
	@ApiModelProperty(value = "ä�ù� �̸�")
	private String roomName;
	@ApiModelProperty(value = "ä�ù� Ÿ�� ���и�")
	private String roomTypeNm;
	@ApiModelProperty(value = "ä�ù� Ÿ�� ����")
	private String roomType;
}
