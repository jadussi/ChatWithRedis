package com.redis.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "ȸ�� ����", description = "����� ���̵�, ��й�ȣ, �̸��� ���� Data Transfer Object")
public class LoginDTO {
	
	@ApiModelProperty(value = "����� ���̵�")
	private String userId; 
	@ApiModelProperty(value = "����� ��й�ȣ")
	private String userPw;
	@ApiModelProperty(value = "����� �̸�")
	private String userNm;
}
