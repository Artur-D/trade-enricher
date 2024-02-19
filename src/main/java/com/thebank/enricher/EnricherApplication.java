package com.thebank.enricher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class EnricherApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnricherApplication.class, args);
	}

}
