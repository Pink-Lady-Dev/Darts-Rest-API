package com.pinkladydev.darts.game;

import com.pinkladydev.darts.player.Dart;
import com.pinkladydev.darts.player.GameType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


public class Game {


    private final String  id;
    private List<GamePlayer> gamePlayers;
    private GameType gameType;


    private Game()
    {
        this.id = UUID.randomUUID().toString();
        this.gamePlayers = new ArrayList<>();
    }

    public static Game startX01(final List<String> playerUsernames,
                                final Integer score){
        Game game = new Game();
        game.gameType = GameType.X01;
        game.gamePlayers = playerUsernames.stream().map(player -> GamePlayer.StartX01(player, score)).collect(toList());
        return game;
    }

    public String getId() {
        return id;
    }

    public List<GamePlayer> getGamePlayers(){
        return this.gamePlayers;
    }


    public GamePlayer getGameUser(String username) {
        return this.gamePlayers.stream()
                .filter(gamePlayer -> username.equals(gamePlayer.getUsername()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Make this no user found exception"));
    }

    public List<Dart> getUserDarts(String userId){
        return getGameUser(userId).getDarts();
    }
}
