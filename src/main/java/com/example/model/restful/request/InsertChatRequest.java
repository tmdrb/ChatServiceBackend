package com.example.model.restful.request;

import lombok.Getter;

@Getter
public class InsertChatRequest {

	private final String userId;
	private final String text;
	private final byte[] content;

	public InsertChatRequest( String userId,
	                          String text,
	                          byte[] content ) {

		this.userId = userId;
		this.text = text;
		this.content = content;
	}
}
