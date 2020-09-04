package com.pinkladydev.darts.game;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Game {

    private final String  id;
    private List<GamePlayer> gamePlayers;
    private GameType gameType;

    public GamePlayer getGameUser(String username) {
        return this.gamePlayers.stream()
                .filter(gamePlayer -> username.equals(gamePlayer.getUsername()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Make this no user found exception"));
    }

    public List<Dart> getUserDarts(String userId){
        return getGameUser(userId).getDarts();
    }


    public static GameBuilder aGameBuilder(){
        return builder();
    }
}
