package com.pinkladydev.darts.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository("FakeDao")
public class FakeUserDataAccessService implements UserDao {

    private static List<User> fakeDB = new ArrayList<>();

    @Override
    public void insertUser(String username, String password){

        fakeDB.add(User.aUserBuilder().username(username).password(password).build());
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

}
