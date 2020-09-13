package com.pinkladydev.darts.game.notifications;

import com.pinkladydev.darts.game.GamePlayer;
import lombok.Getter;

import java.util.List;

@Getter
public class GameMetaNotification extends Notification{

    private final List<GamePlayer> players;
    private final String gameType;

    public GameMetaNotification(List<GamePlayer> players, String gameType) {
        this.players = players;
        this.gameType = gameType;
    }

    @Override
    public String getIdentifier() {
        return "GAME_META";
    }
}
