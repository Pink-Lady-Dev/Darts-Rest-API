package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.Game;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//TODO a lot of this functionality actually should live in the service

@Repository("FakeGameDao")
public class FakeGameAccessService implements GameDao {

    private List<Game> games = new ArrayList<Game>();

    @Override
    public Game getGameData(String gameId) {
        Game temp = games
                .stream()
                .filter(
                        game -> (game.getId().equals(gameId)))
                .findFirst().orElse(null);

        return temp;
    }

    @Override
    public void createGame(Game game) {
        games.add(game);
    }

}
