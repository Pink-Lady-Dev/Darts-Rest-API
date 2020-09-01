package com.pinkladydev.darts.player;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String  id; // connection to user
    private final String username;

    private final List<String> gameLog;
    private final List<String> leagues;

    public Player(final String id, final String username) {
        this.id = id;
        this.username = username;

        this.gameLog = new ArrayList<>();
        this.leagues = new ArrayList<>();

    }
}
