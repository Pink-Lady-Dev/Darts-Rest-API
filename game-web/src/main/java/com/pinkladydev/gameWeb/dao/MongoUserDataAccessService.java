package com.pinkladydev.gameWeb.dao;

import com.mongodb.MongoException;
import com.pinkladydev.gameWeb.api.models.UserRequest;
import com.pinkladydev.gameWeb.dao.entities.UserEntity;
import com.pinkladydev.gameWeb.dao.entities.mappers.UserEntityToUserMapper;
import com.pinkladydev.gameWeb.dao.repositories.UserRepository;
import com.pinkladydev.gameWeb.model.User;
import com.pinkladydev.gameWeb.exceptions.UserDataFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.pinkladydev.gameWeb.dao.entities.mappers.UserEntityToUserMapper.mapUserEntityToUser;
import static com.pinkladydev.gameWeb.dao.entities.mappers.UserRequestToUserEntityMapper.mapUserRequestToUserEntity;

@Repository("Mongo")
public class MongoUserDataAccessService implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void insertUser(UserRequest userRequest){
        try{
            userRepository.save(mapUserRequestToUserEntity(userRequest));
        } catch (MongoException mongoException) {
            throw UserDataFailure.failureToSaveUserToMongo(mongoException.getMessage());
        }
    }

    // This one should probably be deleted
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserEntityToUserMapper::mapUserEntityToUser).collect(Collectors.toList());
    }

    @Override
    public User getUser(String userId) {
        UserEntity userEntity = userRepository.findUserEntityById(userId);
        return userEntity != null ? mapUserEntityToUser(userEntity) : getUserByUsername(userId);
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


