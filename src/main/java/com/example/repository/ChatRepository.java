package com.example.repository;

import com.example.model.entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository< Chat, Chat.Id > {

	@Query( value = "select * from Chat c where c.chat_room_id = ?1 AND c.id < ?2 order by c.created_time desc limit ?3",
		nativeQuery = true)
	List<Chat> getChatList( String chatRoomId, long lastId, int limit );

	@Query( value = "select * from Chat c where c.chat_room_id = ?1 order by c.created_time desc limit ?2",
			nativeQuery = true)
	List<Chat> getChatList( String chatRoomId, int limit );
}
