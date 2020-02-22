package com.pinkladydev.DartsRestAPI;

import com.pinkladydev.DartsRestAPI.dao.UserDao;
import com.pinkladydev.DartsRestAPI.model.CustomUserDetails;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
public class DartsRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DartsRestApiApplication.class, args);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserDao repo) throws Exception{
		if (repo.size() == 0)
		{
			repo.insertUser(new User("user","user"));
		}
		builder.userDetailsService(s -> new CustomUserDetails(repo.getUser(s)));
	}
}
