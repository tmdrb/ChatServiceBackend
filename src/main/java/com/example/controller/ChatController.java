package com.example.controller;

import com.example.model.common.ChatMessage;
import com.example.model.entity.Chat;
import com.example.model.entity.User;
import com.example.model.restful.request.InsertChatRequest;
import com.example.model.restful.request.MakeRoomRequestBody;
import com.example.model.restful.response.ChatListByUserResponse;
import com.example.model.restful.response.ChatResponse;
import com.example.model.restful.response.UserResponse;
import com.example.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

	private final ChatService chatService;
	private final static String CHAT_API = "/chatapp/v1";

	public ChatController( ChatService chatService ) {

		this.chatService = chatService;
	}

	@GetMapping(CHAT_API +"/disconnect")
	public ResponseEntity  disconnect( @RequestParam String userId ){

		System.out.println("dioscpmmect : "+userId);
		chatService.disconnect( userId );

		return ResponseEntity.ok().build();
	}

	@GetMapping(CHAT_API +"/login")
	public ResponseEntity< UserResponse > getUser( @RequestParam String userId ){

		return ResponseEntity.ok().body( chatService.getUser( userId ));
	}

	@GetMapping(CHAT_API +"/users")
	public ResponseEntity< List<UserResponse> > getUsers( ){

		return ResponseEntity.ok().body( chatService.getUsers() );
	}

	@PostMapping( CHAT_API + "/make" )
	public ResponseEntity makeChatRoom( @RequestBody MakeRoomRequestBody requestBody ){

		chatService.invite( requestBody.getChatRoomId(), requestBody.getUserId() );

		return ResponseEntity.ok().body( "" );
	}

	@PutMapping( CHAT_API + "/invite/{chatRoomId}" )
	public ResponseEntity inviteChatRoom( @PathVariable String chatRoomId,
	                                      @RequestBody String userId ){

		chatService.invite( chatRoomId, userId );

		return ResponseEntity.ok().body( "" );
	}

	@GetMapping( CHAT_API + "/{chatRoomId}" )
	public ResponseEntity< List< ChatResponse > > getChat( @PathVariable String chatRoomId,
	                                                       @RequestParam long lastId,
	                                                       @RequestParam int size ){

		List< ChatResponse > chats = chatService.getChat( chatRoomId,
				lastId,
				size );

		System.out.println(chats.size());

		return ResponseEntity.ok().body( chats );
	}

	@PostMapping( CHAT_API + "/{chatRoomId}" )
	public ResponseEntity insertChat( @PathVariable String chatRoomId,
	                                  @RequestBody InsertChatRequest request ){

		chatService.insertChat( request.getUserId(),
				chatRoomId,
				request.getText(),
				request.getContent() );

		return ResponseEntity.ok().body( "" );
	}

	@GetMapping( CHAT_API + "/main" )
	public ResponseEntity< List< ChatListByUserResponse > > getChatRoomList ( @RequestParam("userId") String userId ){

		List chatListByUser = chatService.getChatListByUser( userId );

		System.out.println( chatListByUser );
		return ResponseEntity.ok().body( chatListByUser );
	}


	@MessageMapping("/message/{id}")
	@SendTo("/subscribe/chat/{id}")
	public ChatResponse sendMessage( @DestinationVariable("id") String id,  ChatMessage chatMessage ){

		ChatResponse chatResponse = chatService.insertChat( chatMessage.getUserId(), id, chatMessage.getText(), null );

		return chatResponse;
	}

}
