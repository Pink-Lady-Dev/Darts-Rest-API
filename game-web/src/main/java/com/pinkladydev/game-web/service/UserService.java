package com.pinkladydev.DartsRestAPI.service;

import com.pinkladydev.DartsRestAPI.dao.UserDao;
import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.api.models.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pinkladydev.DartsRestAPI.exceptions.UserDataFailure.UsernameOrIdNotFoundInMongo;

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
            throw UsernameOrIdNotFoundInMongo(userName, usernameNotFoundException.getMessage());
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