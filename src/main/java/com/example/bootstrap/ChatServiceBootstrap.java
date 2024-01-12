package com.example.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootConfiguration
@EnableJpaRepositories("com.example.repository")
@EntityScan( basePackages = {"com.example.model.entity"})
@SpringBootApplication( scanBasePackages = {"com.example"} )
public class ChatServiceBootstrap {

	public static void main( String[] args ) {

		SpringApplication.run( ChatServiceBootstrap.class, args );
	}
}
