package com.example.model.common;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Session {

	private final String id;

	@Builder
	public Session(  ) {
		this.id = UUID.randomUUID().toString();
	}
}
