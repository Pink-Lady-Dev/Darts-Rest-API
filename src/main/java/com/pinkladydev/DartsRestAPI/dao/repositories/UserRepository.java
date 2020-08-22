package com.pinkladydev.DartsRestAPI.dao.repositories;

import com.pinkladydev.DartsRestAPI.dao.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    public UserEntity findUserEntityByUsername(String username);

    public UserEntity findUserEntityById(String Id);

    public List<UserEntity> findAll();
}
