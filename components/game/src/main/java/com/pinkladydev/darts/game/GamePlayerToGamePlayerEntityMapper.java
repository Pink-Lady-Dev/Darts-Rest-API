package com.pinkladydev.darts.game;

import java.util.ArrayList;

import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;

public class GamePlayerToGamePlayerEntityMapper {

    public static GamePlayerEntity map(GamePlayer gamePlayer){
        return aGamePlayerEntityBuilder()
                .id("HashME or PassME?")
                .gameId(gamePlayer.getGameId())
                .gameType(gamePlayer.getGameType())
                .score(gamePlayer.getScore())
                .darts(gamePlayer.getDarts())
                .wins(new ArrayList<>())
                .losses(new ArrayList<>())
                .build();
    }
}
