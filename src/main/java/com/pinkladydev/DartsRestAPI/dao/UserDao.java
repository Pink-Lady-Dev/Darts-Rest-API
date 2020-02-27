package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.User;

import java.util.List;

public interface UserDao {

    void insertUser(User user);

    List<User> getAllUsers();

    User getUser(String userId);

    int size();
}
