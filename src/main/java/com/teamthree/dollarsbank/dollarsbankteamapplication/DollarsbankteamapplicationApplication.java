package com.teamthree.dollarsbank.dollarsbankteamapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.teamthree.dollarsbank.model")
@ComponentScan(basePackages="com")
@EnableJpaRepositories(basePackages = "com.teamthree.dollarsbank.repo")

public class DollarsbankteamapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DollarsbankteamapplicationApplication.class, args);
	}

}
