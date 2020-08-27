package com.pinkladydev.gameWeb.model;

import java.util.List;

public class GameMetaNotification {

    private final List<User> players;
    private final String gameType;

    // Add current game data for reconnect

    private final String IDENTIFIER = "GAME_META";

    public GameMetaNotification(List<User> players, String gameType) {
        this.players = players;
        this.gameType = gameType;
    }

    public List<User> getPlayers() {
        return players;
    }

    public String getGameType() {
        return gameType;
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
