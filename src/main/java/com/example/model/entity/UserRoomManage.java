package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoomManage {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn( name = "chat_room_id" )
	private ChatRoom chatRoom;

	@ManyToOne
	@JoinColumn( name = "user_id" )
	private User user;

	@Override
	public String toString() {
		return "UserRoomManage{" +
				"id=" + id +
				", chatRoom=" + chatRoom.getId() +
				", user=" + user +
				'}';
	}
}
