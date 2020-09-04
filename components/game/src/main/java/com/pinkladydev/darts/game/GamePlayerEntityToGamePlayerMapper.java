package com.pinkladydev.darts.game;

import static com.pinkladydev.darts.game.GamePlayer.aGamePlayerBuilder;

public class GamePlayerEntityToGamePlayerMapper {

    public static GamePlayer map(GamePlayerEntity gamePlayerEntity){
        return aGamePlayerBuilder()
                .gameId(gamePlayerEntity.getGameId())
                .gameType(gamePlayerEntity.getGameType())
                .darts(gamePlayerEntity.getDarts())
                .score(gamePlayerEntity.getScore())
                .build();
    }
}
