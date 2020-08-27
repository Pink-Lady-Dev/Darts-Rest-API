package com.pinkladydev.user;

import java.util.List;

public interface UserDao {

    void insertUser(String username, String password);

    List<User> getAllUsers();

    User getUser(String userId);

    int size();
}
