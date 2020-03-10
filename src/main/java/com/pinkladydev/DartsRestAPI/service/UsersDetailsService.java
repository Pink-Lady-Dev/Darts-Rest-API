package com.pinkladydev.DartsRestAPI.service;

import com.pinkladydev.DartsRestAPI.model.CustomUserDetails;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersDetailsService  implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // Return stuff here
        System.out.println(userName);
        return new CustomUserDetails( new User(UUID.randomUUID(), "bar", "foo"));
    }
}
