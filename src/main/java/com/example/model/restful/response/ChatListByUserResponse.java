package com.example.model.restful.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatListByUserResponse {

	private final String ChatRoomName;
	private final List<String> userNames;

	@Builder
	public ChatListByUserResponse( String chatRoomName, List< String > userNames ) {
		ChatRoomName = chatRoomName;
		this.userNames = userNames;
	}
}
