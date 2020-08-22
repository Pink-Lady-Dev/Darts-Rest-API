package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.dao.entities.UserEntity;
import com.pinkladydev.DartsRestAPI.dao.repositories.UserRepository;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository("Mongo")
public class MongoUserDataAccessService implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void insertUser(User user){
        userRepository.save(simpleUserEntityMap(user));
    }

    // This one should probably be deleted
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(this::simpleUserMap).collect(Collectors.toList());
    }

    @Override
    public User getUser(String userId) {
        UserEntity userEntity = userRepository.findUserEntityById(userId);
        return userEntity != null ? simpleUserMap(userEntity) : getUserByUsername(userId);
    }

    // This one should probably be deleted
    @Override
    public int size() {
        return userRepository.findAll().size();
    }

    private User simpleUserMap(UserEntity userEntity){
        return new User(userEntity.username, userEntity.password, userEntity.id);
    }

    private UserEntity simpleUserEntityMap(User user){
        return new UserEntity(user.getUsername(), user.getPassword());
    }

    private User getUserByUsername(String username){
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        return userEntity != null ? simpleUserMap(userEntity) : null;
    }
}


