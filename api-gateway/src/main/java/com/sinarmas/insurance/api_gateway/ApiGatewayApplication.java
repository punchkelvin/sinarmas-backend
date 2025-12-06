package com.sinarmas.insurance.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {

		System.out.println("Api Gateway running");
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
