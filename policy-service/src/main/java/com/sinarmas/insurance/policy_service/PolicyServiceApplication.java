package com.sinarmas.insurance.policy_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PolicyServiceApplication {

	public static void main(String[] args) {

		System.out.println("Policy service is running");
		SpringApplication.run(PolicyServiceApplication.class, args);
	}

}
