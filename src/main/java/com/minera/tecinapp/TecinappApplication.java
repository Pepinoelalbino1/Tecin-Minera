package com.minera.tecinapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.minera.tecinapp")
public class TecinappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TecinappApplication.class, args);
	}

}
