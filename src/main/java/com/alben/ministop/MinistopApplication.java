package com.alben.ministop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.*;

@SpringBootApplication
@EnableJpaRepositories("com.alben.ministop.jpa")
public class MinistopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinistopApplication.class, args);
	}

}
