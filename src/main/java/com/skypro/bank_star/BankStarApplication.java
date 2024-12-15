package com.skypro.bank_star;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class BankStarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankStarApplication.class, args);
	}

}
