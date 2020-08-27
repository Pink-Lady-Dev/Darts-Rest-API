package com.pinkladydev.gameWeb.dao;

import com.pinkladydev.gameWeb.model.User;
import com.pinkladydev.gameWeb.api.models.UserRequest;

import java.util.List;

public interface UserDao {

    void insertUser(UserRequest userRequest);

    List<User> getAllUsers();

    User getUser(String userId);

    int size();
}
