package com.pinkladydev.darts.game;

import lombok.Getter;

import java.util.List;

@Getter
public class GameMetaNotification {

    private final List<GamePlayer> players;
    private final String gameType;

    // TODO - Add current game data for reconnect
    private final String IDENTIFIER = "GAME_META";

    public GameMetaNotification(List<GamePlayer> players, String gameType) {
        this.players = players;
        this.gameType = gameType;
    }
}
