package com.pinkladydev.DartsRestAPI.service;

import com.pinkladydev.DartsRestAPI.dao.UserDao;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("FakeDao") UserDao userDao){
        this.userDao = userDao;
    }
    public int insertUser (User user) {
        return userDao.insertUser(user);
    }

    public List<User> getAllUsers () {
        return userDao.getAllUsers();
    }
}
