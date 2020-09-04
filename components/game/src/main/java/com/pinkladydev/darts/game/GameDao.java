package com.pinkladydev.darts.game;

import java.util.List;

public interface GameDao {

    // GET /{Game} - read data
    GamePlayer getGamePlayer(String gameId, String gamePlayerId);

    List<GamePlayer> getGamePlayers(String gameId);

    Game getTotalGame(String gameId);

    // POST /{Game} - create new
    void createGame(GamePlayer gamePlayer); //May need to be done with a Unique id


}
