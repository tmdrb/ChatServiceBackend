package com.example.repository;

import com.example.model.entity.User;
import com.example.model.entity.UserRoomManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoomManageRepository extends JpaRepository< UserRoomManage, Long > {

	List< UserRoomManage > findUserRoomManageByChatRoomId( String chatRoomId );

	List< UserRoomManage > findUserRoomManageByUserId( String userId );

}
