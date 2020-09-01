package com.pinkladydev.darts.game;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


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
