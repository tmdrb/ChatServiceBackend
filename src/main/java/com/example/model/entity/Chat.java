package com.example.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {

	@EmbeddedId
	private Id id;

	@ManyToOne
	@JoinColumn( name = "user_id" )
	private User user;

	@Lob
	private byte[] content;

	@Lob
	private String text;

	private ZonedDateTime createdTime;


	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable
	@Builder
	@ToString( exclude = "chatRoom")
	public static class Id implements Serializable {

		@Column( name = "id")
		private long id;

		@ManyToOne
		@JoinColumn( name = "chat_room_id" )
		private ChatRoom chatRoom;
	}
}
