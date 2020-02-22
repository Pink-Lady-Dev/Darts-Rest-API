package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.DartThrow;
import com.pinkladydev.DartsRestAPI.model.Game;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;


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

        if (temp == null)
        {
            User[] tempUs = {new User("Jake Carlson", UUID.randomUUID().toString()), new User("Nick Clason", UUID.randomUUID().toString())};
            games.add(new Game(gameId, tempUs, "X01"));
            temp = new Game(gameId, tempUs, "X01");
        }
        return temp;
    }

    @Override
    public void createGame(Game game) {
        games.add(game);
    }

}
