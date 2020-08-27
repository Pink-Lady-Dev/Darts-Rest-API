package com.pinkladydev.gameWeb.service;

import com.pinkladydev.gameWeb.dao.UserDao;
import com.pinkladydev.gameWeb.model.User;
import com.pinkladydev.gameWeb.api.models.UserRequest;
import com.pinkladydev.gameWeb.exceptions.UserDataFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("Mongo") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        try{
            return userDao.getUser(userName);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            throw UserDataFailure.UsernameOrIdNotFoundInMongo(userName, usernameNotFoundException.getMessage());
        }
    }


    public void insertUser (UserRequest userRequest) {
        userDao.insertUser(userRequest);
    }

    public List<User> getAllUsers () {
        return userDao.getAllUsers();
    }

    public User getUser(String id) {
        return userDao.getUser(id);
    }
}
