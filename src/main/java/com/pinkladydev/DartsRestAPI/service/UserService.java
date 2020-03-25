package com.pinkladydev.DartsRestAPI.service;

import com.pinkladydev.DartsRestAPI.dao.UserDao;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("FakeDao") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // Return stuff here
        System.out.println(userName);

        if (userDao.getAllUsers().isEmpty())
        {
            userDao.insertUser(new User(UUID.randomUUID(), "jakeocinco", "password"));
            userDao.insertUser(new User(UUID.randomUUID(), "nickClason", "password"));
        }

        return userDao.getUser(userName);
    }


    public void insertUser (User user) {
        userDao.insertUser(user);
    }

    public List<User> getAllUsers () {
        return userDao.getAllUsers();
    }

    public User getUser(String id) {
        return userDao.getUser(id);
    }
}
