package com.pinkladydev.user;

import com.mongodb.MongoException;
import com.pinkladydev.user.entities.UserEntity;
import com.pinkladydev.user.entities.mappers.UserEntityToUserMapper;
import com.pinkladydev.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.pinkladydev.user.entities.UserEntity.aUserEntityBuilder;


@Repository("Mongo")
public class MongoUserDataAccessService implements UserDao {

    private final UserRepository userRepository;

    @Autowired
    public MongoUserDataAccessService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void insertUser(String username, String password){
        try{
            userRepository.save(aUserEntityBuilder().username(username).password(password).build());
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
        return userEntity != null ? UserEntityToUserMapper.mapUserEntityToUser(userEntity) : getUserByUsername(userId);
    }

    // This one should probably be deleted
    @Override
    public int size() {
        return userRepository.findAll().size();
    }

    private User getUserByUsername(String username){
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        return userEntity != null ? UserEntityToUserMapper.mapUserEntityToUser(userEntity) : null;
    }
}


