package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("FakeDao")
public class FakeUserDataAccessService implements UserDao {

    private static List<User> fakeDB = new ArrayList<>();

    @Override
    public int insertUser(User user){


        fakeDB.add(user);
        return 1;
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
            if (u.getName().equals(userId))
                return u;
        return null;
    }

    @Override
    public int size() {
        return fakeDB.size();
    }
}
