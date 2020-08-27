package com.pinkladydev.gameWeb.dao;

import com.pinkladydev.gameWeb.model.Game;

public interface GameDao {

    // GET /{Game} - read data
    Game getGameData(String gameId);

    // POST /{Game} - create new
    void createGame(Game game); //May need to be done with a Unique id


}
