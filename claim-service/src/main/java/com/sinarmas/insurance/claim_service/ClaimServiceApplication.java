package com.sinarmas.insurance.claim_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClaimServiceApplication {

	public static void main(String[] args) {
		System.out.println("Claim service runnign");
		SpringApplication.run(ClaimServiceApplication.class, args);
	}

}
