package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Encoding the password (BCrypt)
@Configuration
public class PasswordConfiguration {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// The encoder strength is = 10
		return new BCryptPasswordEncoder(10);
	}
}
