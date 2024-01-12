package com.example.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatRoom {

	@Id
	private String id;

	private ZonedDateTime createdTime;

	@OneToMany( mappedBy = "id.chatRoom", fetch = FetchType.LAZY)
	private List< Chat > chatList;

	@OneToMany( mappedBy = "chatRoom", fetch = FetchType.LAZY)
	private List< UserRoomManage > userRoomList;

}
