package com.pinkladydev.darts.game;

import lombok.Getter;

@Getter
public class DartNotification {

    // Throw information
    private final String  id; // Identifier for throw
    private final int throwNumber;
    private final int points; // Calculated score
    private final int pie; // Actual number hit
    private final boolean isDouble;
    private final boolean isTriple;
    private final String username;
    private final Integer score;

    private final String IDENTIFIER = "DART";

    public DartNotification(Dart dart, String username, Integer score) {
        this.id = dart.getId();
        this.throwNumber = dart.getThrowNumber();
        this.points = dart.getPoints();
        this.pie = dart.getPie();
        this.isDouble = dart.isDouble();
        this.isTriple = dart.isTriple();
        this.username = username;
        this.score = score;
    }
}
