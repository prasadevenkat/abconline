package com.abconline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.abconline.models.*")
@EnableJpaRepositories("com.abconline.repositories.*")
public class AbconlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbconlineApplication.class, args);
	}
}
