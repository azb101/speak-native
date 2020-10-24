package com.abuseapp.speaknativeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpeakNativeApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpeakNativeApiApplication.class, args);
		System.out.println("Started");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpeakNativeApiApplication.class);
	}
}
