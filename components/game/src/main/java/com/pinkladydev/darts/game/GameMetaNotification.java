package com.pinkladydev.darts.game;

import java.util.List;

public class GameMetaNotification {

    private final List<GamePlayer> players;
    private final String gameType;

    // Add current game data for reconnect

    private final String IDENTIFIER = "GAME_META";

    public GameMetaNotification(List<GamePlayer> players, String gameType) {
        this.players = players;
        this.gameType = gameType;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public String getGameType() {
        return gameType;
    }

    public String getIDENTIFIER() {
        return IDENTIFIER;
    }
}
