package com.abuseapp.speaknativeappapijava;

import com.abuseapp.speaknativeappapijava.endpoints.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@SpringBootApplication
public class SpeakNativeAppApiJavaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpeakNativeAppApiJavaApplication.class, args);
		System.out.println("Started");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpeakNativeAppApiJavaApplication.class);
	}
}
