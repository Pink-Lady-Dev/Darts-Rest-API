package com.pinkladydev.DartsRestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

// TODO - investigate using basePackagesClasses -- seems safer more direct
@SpringBootApplication(scanBasePackages={"com.pinkladydev.gameWeb", "com.pinkladydev.game","com.pinkladydev.user"})
@EnableMongoRepositories(basePackageClasses = {com.pinkladydev.user.repositories.UserRepository.class})
public class DartsRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DartsRestApiApplication.class, args);
	}
}
