package com.example.demo;

import com.example.demo.security.CustomUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CustomUserDetailsService userService, BCryptPasswordEncoder encoder) {
		return args -> {
			userService.registerUser(
				"Admin User",
				"admin@example.com",
				encoder.encode("password123"),
				"ADMIN"
			);
		};
	}
}