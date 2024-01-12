package com.example.model.restful.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private String id;
	private String name;
}
