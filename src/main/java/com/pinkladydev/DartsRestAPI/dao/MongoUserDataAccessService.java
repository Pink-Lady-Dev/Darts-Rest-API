package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.dao.entities.UserEntity;
import com.pinkladydev.DartsRestAPI.dao.entities.mappers.UserEntityToUserMapper;
import com.pinkladydev.DartsRestAPI.dao.repositories.UserRepository;
import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.api.models.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.pinkladydev.DartsRestAPI.dao.entities.mappers.UserEntityToUserMapper.mapUserEntityToUser;
import static com.pinkladydev.DartsRestAPI.dao.entities.mappers.UserRequestToUserEntityMapper.mapUserRequestToUserEntity;

@Repository("Mongo")
public class MongoUserDataAccessService implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void insertUser(UserRequest userRequest){
        userRepository.save(mapUserRequestToUserEntity(userRequest));
    }

    // This one should probably be deleted
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserEntityToUserMapper::mapUserEntityToUser).collect(Collectors.toList());
    }

    @Override
    public User getUser(String userId) {
        UserEntity userEntity = userRepository.findUserEntityById(userId);
        User u = userEntity != null ? mapUserEntityToUser(userEntity) : getUserByUsername(userId);
        return u;
    }

    // This one should probably be deleted
    @Override
    public int size() {
        return userRepository.findAll().size();
    }

    private User getUserByUsername(String username){
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        return userEntity != null ? mapUserEntityToUser(userEntity) : null;
    }
}


