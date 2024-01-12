package com.example.model.restful.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ChatResponse {

	private final long id;
	private final String text;
	private final ZonedDateTime createTime;
	private final String userName;

	@Builder
	public ChatResponse( long id,
	                     String text,
	                     ZonedDateTime createTime,
	                     String userName ) {
		this.id = id;
		this.text = text;
		this.createTime = createTime;
		this.userName = userName;
	}
}
