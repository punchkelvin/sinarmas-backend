package com.sinarmas.insurance.customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {

		System.out.println("Customer Service Running");
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
