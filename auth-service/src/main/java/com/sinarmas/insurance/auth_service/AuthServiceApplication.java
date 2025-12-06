package com.sinarmas.insurance.auth_service;

import com.sinarmas.insurance.auth_service.service.KeyGenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		System.out.println("Auth service running");
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
