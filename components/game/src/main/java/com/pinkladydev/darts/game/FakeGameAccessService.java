package com.pinkladydev.darts.game;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


//TODO a lot of this functionality actually should live in the service

@Repository("FakeGameDao")
public class FakeGameAccessService implements GameDao {

    private List<GamePlayer> gamePlayers = new ArrayList<>();

    @Override
    public GamePlayer getGamePlayer(String gameId, String gamePlayerId) {
        return null;
    }

    @Override
    public List<GamePlayer> getGamePlayers(String gameId) {
        return null;
    }

    @Override
    public Game getTotalGame(String gameId) {
        return null;
    }

    @Override
    public void save(GamePlayer gamePlayer) {
        gamePlayers.add(gamePlayer);
    }

}
