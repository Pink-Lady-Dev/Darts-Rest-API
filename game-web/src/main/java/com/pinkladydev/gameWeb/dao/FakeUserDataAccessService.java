package com.pinkladydev.gameWeb.dao;

import com.pinkladydev.gameWeb.model.User;
import com.pinkladydev.gameWeb.api.models.UserRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("FakeDao")
public class FakeUserDataAccessService implements UserDao {

    private static List<User> fakeDB = new ArrayList<>();

    @Override
    public void insertUser(UserRequest user){

        fakeDB.add(simpleUserMap(user));
    }

    @Override
    public List<User> getAllUsers() {
        return fakeDB;
    }

    @Override
    public User getUser(String userId) {
        for (User u : fakeDB)
            if (u.getId().equals(userId))
                return u;
        for (User u : fakeDB)
            if (u.getUsername().equals(userId))
                return u;
        return null;
    }

    @Override
    public int size() {
        return fakeDB.size();
    }

    private User simpleUserMap(UserRequest userRequest){
        return User.aUserBuilder().username(userRequest.getUsername()).password(userRequest.getPassword()).id(UUID.randomUUID().toString()).build();
    }
}
