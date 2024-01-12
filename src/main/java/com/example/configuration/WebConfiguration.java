package com.example.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings( CorsRegistry registry ) {

		registry.addMapping( "/chatapp/v1/**" )
				.allowedOrigins("*")
				.allowedMethods( "GET", "POST","PUT" );
	}
}
