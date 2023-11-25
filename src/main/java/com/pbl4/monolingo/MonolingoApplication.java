package com.pbl4.monolingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication

public class MonolingoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonolingoApplication.class, args);
	}

}
