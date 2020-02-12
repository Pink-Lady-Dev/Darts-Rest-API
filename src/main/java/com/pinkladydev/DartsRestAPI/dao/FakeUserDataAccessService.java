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
    public int insertUser(UUID id, User user){
        fakeDB.add(new User(id, user.getName()));
        return 1;
    }

    @Override
    public List<User> getAllUsers() {
        if (fakeDB.isEmpty()){
            fakeDB.add(new User(UUID.randomUUID(), "Jake Carlson"));
            fakeDB.add(new User(UUID.randomUUID(), "Nick Clason"));
        }
        return fakeDB;
    }
}
