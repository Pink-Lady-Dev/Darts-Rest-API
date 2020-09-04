package com.pinkladydev.darts.game;

import lombok.Getter;

@Getter
public class RemovedDartNotification {

    // Throw information
    private final String username;
    private final Integer score;

    private final String IDENTIFIER = "REMOVED_DART";

    public RemovedDartNotification(String username, Integer score) {
        this.username = username;
        this.score = score;
    }
}
