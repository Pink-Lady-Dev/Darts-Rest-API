package com.pinkladydev.DartsRestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackageClasses={
		com.pinkladydev.darts.player.PlayerService.class,
		com.pinkladydev.darts.game.GameService.class,
		com.pinkladydev.darts.user.UserService.class,
		com.pinkladydev.darts.authentication.SecurityConfigurer.class,
		com.pinkladydev.darts.web.UserController.class})
@EnableMongoRepositories(basePackageClasses = {
		com.pinkladydev.darts.user.repositories.UserRepository.class,
		com.pinkladydev.darts.game.GameRepository.class,
		com.pinkladydev.darts.player.PlayerRepository.class})
public class DartsRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DartsRestApiApplication.class, args);
	}
}
