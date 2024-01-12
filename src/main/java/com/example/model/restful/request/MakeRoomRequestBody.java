package com.example.model.restful.request;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class MakeRoomRequestBody {

	private final String userId;
	private final String chatRoomId;

	public MakeRoomRequestBody( String userId,
	                            String chatRoomId ) {

		this.userId = userId;
		if( StringUtils.isEmpty( chatRoomId ) ){

			this.chatRoomId = UUID.randomUUID().toString();
		} else {

			this.chatRoomId = chatRoomId;
		}
	}
}
