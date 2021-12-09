package com.api.iqtec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class IqtecRestApplication extends SpringBootServletInitializer {

	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(IqtecRestApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(IqtecRestApplication.class, args);
	}

}
