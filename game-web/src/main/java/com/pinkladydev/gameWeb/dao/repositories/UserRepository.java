package com.pinkladydev.gameWeb.dao.repositories;

import com.pinkladydev.gameWeb.dao.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    public UserEntity findUserEntityByUsername(String username);

    public UserEntity findUserEntityById(String Id);

    public List<UserEntity> findAll();
}
