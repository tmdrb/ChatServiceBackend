package com.example.service;

import com.example.model.entity.Chat;
import com.example.model.entity.ChatRoom;
import com.example.model.entity.User;
import com.example.model.entity.UserRoomManage;
import com.example.model.restful.response.ChatListByUserResponse;
import com.example.model.restful.response.ChatResponse;
import com.example.model.restful.response.UserResponse;
import com.example.repository.ChatRepository;
import com.example.repository.ChatRoomRepository;
import com.example.repository.UserRepository;
import com.example.repository.UserRoomManageRepository;
import com.example.session.SessionManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ChatService {

	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;
	private final UserRoomManageRepository userRoomManageRepository;
	private final SessionManager sessionManager;


	public ChatService( ChatRepository chatRepository,
	                    ChatRoomRepository chatRoomRepository,
	                    UserRepository userRepository,
	                    UserRoomManageRepository userRoomManageRepository,
	                    SessionManager sessionManager ) {

		this.chatRepository = chatRepository;
		this.chatRoomRepository = chatRoomRepository;
		this.userRepository = userRepository;
		this.userRoomManageRepository = userRoomManageRepository;
		this.sessionManager = sessionManager;
	}

	public List< ChatResponse > getChat( String chatRoomId,
	                                     long lastId,
	                                     int limit ){

		List< ChatResponse > response = null;

		if( lastId == -1 ) {

			response = chatRepository.getChatList( chatRoomId, limit ).stream()
					.map( ( chat ) ->
							ChatResponse.builder()
									.id( chat.getId().getId() )
									.userName( chat.getUser().getName() )
									.text( chat.getText() )
									.createTime( chat.getCreatedTime() )
									.build() )
					.collect( Collectors.toUnmodifiableList() );
		} else {

			response = chatRepository.getChatList( chatRoomId, lastId, limit ).stream()
					.map( ( chat ) ->
							ChatResponse.builder()
									.id( chat.getId().getId() )
									.userName( chat.getUser().getName() )
									.text( chat.getText() )
									.createTime( chat.getCreatedTime() )
									.build() )
					.collect( Collectors.toUnmodifiableList() );
		}
		return response;
	}

	@Transactional
	public ChatResponse insertChat( String userId,
	                        String chatRoomId,
	                        String text,
	                        byte[] content ){

		ChatRoom chatRoom = chatRoomRepository.findById( chatRoomId ).get();

		User user = userRepository.findById( userId ).get();

		if( !match( chatRoom,user ) )
			throw new NoSuchElementException();

		ZonedDateTime now = ZonedDateTime.now();

		Chat chat = Chat.builder()
					.id( Chat.Id.builder()
							.id( now.toEpochSecond() )
							.chatRoom( chatRoom )
							.build()  )
					.content( content )
					.text( text )
					.user( user )
					.createdTime( now )
					.build();

		chatRepository.save( chat );
		chatRepository.flush();

		System.out.println("############### save ################");

		return ChatResponse.builder()
				.id( chat.getId().getId() )
				.text( chat.getText() )
				.createTime( chat.getCreatedTime() )
				.userName( chat.getUser().getName() )
				.build();
	}

	@Transactional
	public void invite( String chatRoomId,
	                    String userId ){

		System.out.println(chatRoomId + ":" + userId);
		ChatRoom chatRoom = chatRoomRepository.findById( chatRoomId ).get();

		User user = userRepository.findById( userId ).get();

		if( !match( chatRoom, user ) ){

			userRoomManageRepository.save( UserRoomManage.builder()
							.user( user )
							.chatRoom( chatRoom )
					.build() );
		}
	}

	@Transactional
	public List getChatListByUser( String userId ){

		List< ChatListByUserResponse > response = userRoomManageRepository.findUserRoomManageByUserId( userId ).stream()
				.map( userRoomManage -> ChatListByUserResponse
						.builder()
						.chatRoomName( userRoomManage.getChatRoom().getId() )
						.userNames( userRoomManage.getChatRoom()
								.getUserRoomList()
								.stream()
								.map( ( users ) -> users
										.getUser()
										.getName() )
								.collect( Collectors.toList() ) )
						.build() )
				.collect( Collectors.toList() );

		System.out.println(response.size() );
		return response;
	}

	public UserResponse getUser( String userId ){

		if( sessionManager.isConnected( userId ) ){

			throw new IllegalArgumentException();
		}

		User user = userRepository.findById( userId ).get();

		sessionManager.register( userId );

		return UserResponse.builder()
				.id( user.getId() )
				.name( user.getName() )
				.build();
	}

	public List< UserResponse > getUsers(){

		return userRepository.findAll().stream().map( user -> UserResponse.builder()
				.id( user.getId() )
				.name( user.getName() )
				.build() )
				.collect( Collectors.toList());
	}

	public void disconnect( String userId ){

		sessionManager.unRegister( userId );
	}

	private boolean match( ChatRoom chatRoom,
	                       User user ){

		List< UserRoomManage > userRoomManages = userRoomManageRepository.findUserRoomManageByChatRoomId( chatRoom.getId() );

		boolean exist = userRoomManages.stream()
				.anyMatch( userRoomManage -> {

					return userRoomManage.getUser().getId().equals( user.getId() );
				} );

		return exist;
	}


}
