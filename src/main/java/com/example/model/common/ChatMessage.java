package com.example.model.common;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ChatMessage {

	private final String userId;
	private final String text;
	private final ZonedDateTime createTime;

	@Builder
	public ChatMessage( String userId,
	                    String text ) {

		this.userId = userId;
		this.text = text;
		this.createTime = ZonedDateTime.now();
	}
}
