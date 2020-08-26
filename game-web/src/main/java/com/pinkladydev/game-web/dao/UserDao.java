package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.api.models.UserRequest;

import java.util.List;

public interface UserDao {

    void insertUser(UserRequest userRequest);

    List<User> getAllUsers();

    User getUser(String userId);

    int size();
}
