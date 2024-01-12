package com.example.session;

import com.example.model.common.Session;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

	private final Map< String, Session > sessionHashMap = new ConcurrentHashMap<>();

	public void register( String userId ){

		if ( !isConnected( userId ) ){

			sessionHashMap.put( userId, new Session() );
		}
	}

	public void unRegister( String userId ){

		if ( isConnected( userId ) ){

			sessionHashMap.remove( userId );
		}
	}

	public boolean isConnected( String userId ){

		return sessionHashMap.keySet().contains( userId );
	}
}
