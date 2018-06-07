package com.dzj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ObjectMapper objectMapper() {
		
		return new ObjectMapper();
	}

	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			//basePath = "C:/house/image";
			
			registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/MyVedio/upload/");
		} else {
			registry.addResourceHandler("/upload/**").addResourceLocations("file:///usr/local/MyVedio/upload/");
			
		}
	/*	String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			//basePath = "C:/house/image";
			
			registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/house/image/upload/");
		} else {
			registry.addResourceHandler("/upload/**").addResourceLocations("file:////usr/local/house/work/image/upload/");
			
		}*/
	}
	
	
	
}
