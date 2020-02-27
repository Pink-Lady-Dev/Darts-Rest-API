package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    int insertUser(User user);
    List<User> getAllUsers();

    User getUser(String userId);
    // TODO get user by id

    int size();
}
