package com.example;

import com.example.bootstrap.ChatServiceBootstrap;
import com.example.model.entity.Chat;
import com.example.model.entity.ChatRoom;
import com.example.model.entity.User;
import com.example.model.entity.UserRoomManage;
import com.example.model.restful.response.ChatListByUserResponse;
import com.example.repository.ChatRepository;
import com.example.repository.ChatRoomRepository;
import com.example.repository.UserRepository;
import com.example.repository.UserRoomManageRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = ChatServiceBootstrap.class)
//@DataJpaTest( showSql = true )
//@ContextConfiguration( classes = ChatServiceBootstrap.class )
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepoTest {

	@Autowired
	UserRepository repository;

	@Autowired
	UserRoomManageRepository userRoomManageRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@Autowired
	ChatRepository chatRepository;



	@Test
	void insert(){

		User user = User.builder()
				.id( "simhan" )
				.name( "심한" )
				.build();

		repository.save( user );

		User user1 = User.builder()
				.id( "amb" )
				.name( "서영락대리" )
				.build();

		User user2 = User.builder()
				.id( "so" )
				.name( "김김소" )
				.build();

		repository.save( user );

		repository.save( user1 );
		repository.save( user2 );

		repository.flush();
	}


	@Test
//	@Transactional
	void insertChatRoom(){

		ChatRoom chatRoom = ChatRoom.builder()
				.id( "netand_ctl_backend" )
				.createdTime( ZonedDateTime.now() )
				.build();

		UserRoomManage userRoomManage = UserRoomManage.builder()
				.chatRoom( chatRoom )
				.user( repository.findById( "s4" ).get() )
				.build();

		ChatRoom chatRoom1 = ChatRoom.builder()
				.id( "netand_ctl_cs" )
				.createdTime( ZonedDateTime.now() )
				.build();

		ChatRoom chatRoom2 = ChatRoom.builder()
				.id( "netand_ctl_total" )
				.createdTime( ZonedDateTime.now() )
				.build();
		UserRoomManage userRoomManage2 = UserRoomManage.builder()
				.chatRoom( chatRoom1 )
				.user( repository.findById( "so" ).get() )
				.build();

		UserRoomManage userRoomManage3 = UserRoomManage.builder()
				.chatRoom( chatRoom2 )
				.user( repository.findById( "amb" ).get() )
				.build();

		chatRoomRepository.save( chatRoom );
		chatRoomRepository.flush();
		userRoomManageRepository.save( userRoomManage );
		userRoomManageRepository.flush();

		chatRoomRepository.save( chatRoom1 );
		chatRoomRepository.flush();
		userRoomManageRepository.save( userRoomManage2 );
		userRoomManageRepository.flush();


		chatRoomRepository.save( chatRoom2 );
		chatRoomRepository.flush();
		userRoomManageRepository.save( userRoomManage3 );

		userRoomManageRepository.flush();

	}

	@Test
	void re(){

		List< UserRoomManage > userByChatRoomId = userRoomManageRepository.findUserRoomManageByChatRoomId( "123" );

		System.out.println(userByChatRoomId);
	}

	@Test
	void insertChat(){

		ChatRoom chatRoom = chatRoomRepository.findById( "123" ).get();
		ChatRoom chatRoom1 = chatRoomRepository.findById( "456" ).get();


		User user = repository.findById( "seunggyu" ).get();
		User user1 = repository.findById( "abc" ).get();

		List< UserRoomManage > userRoomManages = userRoomManageRepository.findUserRoomManageByChatRoomId( "123" );

		boolean b = userRoomManages.stream()
				.anyMatch( userRoomManage -> {

					return userRoomManage.getUser().getId().equals( user.getId() );
				} );

		if( b ){

			ZonedDateTime now = ZonedDateTime.now();

			Chat chat = Chat.builder()
					.id( Chat.Id.builder()
							.id( now.toEpochSecond() )
							.chatRoom( chatRoom )
							.build()  )
					.content( null )
					.text( "hdzc" )
					.user( user )
					.createdTime( now )
					.build();
			chatRepository.save( chat );

//			chatRepository.flush();
//			Chat chat1 = Chat.builder()
//					.id( Chat.Id.builder()
//							.id( now.toEpochSecond() )
//							.chatRoom( chatRoom )
//							.build()  )
//					.content( null )
//					.text( "hdzc" )
//					.user( user )
//					.createdTime( now )
//					.build();
//			chatRepository.save( chat1 );

			Chat chat2 = Chat.builder()
					.id( Chat.Id.builder()
							.id( now.toEpochSecond() )
							.chatRoom( chatRoom1 )
							.build()  )
					.content( null )
					.text( "zzxcvxzcv" )
					.user( user1 )
					.createdTime( now )
					.build();
			chatRepository.save( chat2 );
		}
	}

	@Test
	@Transactional
	public void ChatFindTest(){

		ChatRoom chatRoom = chatRoomRepository.findById( "123" ).get();

		List< UserRoomManage > userRoomList = chatRoom.getUserRoomList();
		List< Chat > chatList = chatRoom.getChatList();

		System.out.println(chatList);
		System.out.println(userRoomList);
	}

	@Test
	@Transactional
	public void pageChat(){

		List< Chat > chatList = chatRepository.getChatList( "123", 10, 50 );

		System.out.println(chatList);
	}

	@Test
	@Transactional
	public void chatRoomtest(){

		List< ChatListByUserResponse > response = userRoomManageRepository.findUserRoomManageByUserId( "abc" ).stream()
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
						.build()
				)
				.collect( Collectors.toList() );
		System.out.println(response);
	}


}
