package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.DartThrow;
import com.pinkladydev.DartsRestAPI.model.Game;
import com.pinkladydev.DartsRestAPI.model.User;

import java.util.List;

public interface GameDao {

    // GET /{Game} - read data
    Game getGameData(String gameId);

    // POST /{Game} - create new
    void createGame(Game game); //May need to be done with a Unique id


}
