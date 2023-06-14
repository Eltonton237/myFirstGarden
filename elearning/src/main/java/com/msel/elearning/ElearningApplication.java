package com.msel.elearning;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ElearningApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(ElearningApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		System.out.println("Allons y maintenant");
	}
	

}
