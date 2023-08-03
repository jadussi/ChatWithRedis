package com.redis.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "채팅방 정보", description = "채팅방아이디, 채팅방 이름, 채팅방 타입을 가진 Data Transfer Object")
public class ChatDTO {
	@ApiModelProperty(value = "채팅방 아이디")
	private String roomId;
	@ApiModelProperty(value = "채팅방 이름")
	private String roomName;
	@ApiModelProperty(value = "채팅방 타입 구분명")
	private String roomTypeNm;
	@ApiModelProperty(value = "채팅방 타입 구분")
	private String roomType;
	@ApiModelProperty(value = "채팅방 참여자 아이디")
	private String userId;
	@ApiModelProperty(value = "채팅방 참여자 수")
	private String partiNum;
	@ApiModelProperty(value = "현재 참여자 수")
	private String prePartiNum;
	@ApiModelProperty(value = "참여가능 여부")
	private String partiYn;
}
