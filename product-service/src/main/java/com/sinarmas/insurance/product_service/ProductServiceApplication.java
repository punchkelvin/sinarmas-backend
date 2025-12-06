package com.sinarmas.insurance.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {

		System.out.println("Product Service Running");
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
