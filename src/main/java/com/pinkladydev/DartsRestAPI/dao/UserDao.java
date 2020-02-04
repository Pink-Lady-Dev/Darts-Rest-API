package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    int insertUser(UUID id, User user);
    List<User> getAllUsers();

    default int insertUser(User user){
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

}
