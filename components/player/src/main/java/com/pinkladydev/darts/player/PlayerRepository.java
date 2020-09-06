package com.pinkladydev.darts.player;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {

    PlayerEntity findByUsername(String username);

    List<PlayerEntity> findAll();
}
