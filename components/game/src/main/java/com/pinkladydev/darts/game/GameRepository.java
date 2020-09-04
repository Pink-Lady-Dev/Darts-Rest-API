package com.pinkladydev.darts.game;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//Rename this
public interface GameRepository extends MongoRepository<GamePlayerEntity, String> {

    GamePlayerEntity findGamePlayerEntityById(String Id);

    //Use this to get all players in game
    List<GamePlayerEntity> findAllGamePlayerEntityByGameId(String GameId);
}
