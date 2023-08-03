package com.redis.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "회원 정보", description = "사용자 아이디, 비밀번호, 이름을 가진 Data Transfer Object")
public class LoginDTO {
	
	@ApiModelProperty(value = "사용자 아이디")
	private String userId; 
	@ApiModelProperty(value = "사용자 비밀번호")
	private String userPw;
	@ApiModelProperty(value = "사용자 이름")
	private String userNm;
}
