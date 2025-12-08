package com.sinarmas.insurance.payment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		System.out.println("Payment Service Running");
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
