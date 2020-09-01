package com.pinkladydev.darts.player;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

// TODO - This will need to get moved to either player or game
public class Dart {

    // Throw information
    private final String  id; // Identifier for throw
    private final int throwNumber;
    private final int points; // Calculated score
    private final int pie; // Actual number hit
    private final boolean isDouble;
    private final boolean isTriple;

    public Dart(Dart dart) {
        this.id = dart.id;
        this.throwNumber = dart.throwNumber;
        this.points = dart.points;
        this.pie = dart.pie;
        this.isDouble = dart.isDouble;
        this.isTriple = dart.isTriple;
    }

    public Dart(
            @JsonProperty("throwNumber") int  throwNumber,
            @JsonProperty("pie") int pie,
            @JsonProperty("double") boolean isDouble,
            @JsonProperty("triple") boolean isTriple) {


        // Dart scoring
        this.id = UUID.randomUUID().toString();
        this.throwNumber = throwNumber;
        this.pie = pie;


        // This is just a check to make sure they are not both true
        // Will most likely need to be an exception
        if (isDouble && isTriple) {
            this.isDouble = false;
            this.isTriple = false;
        } else {
            this.isDouble = isDouble;
            this.isTriple = isTriple;
        }

        this.points = this.pie * Math.max(1, (this.isDouble ? 1 : 0) * 2) * Math.max(1, (this.isTriple ? 1 : 0) * 3);

    }

    public int getPoints() {
        return points;
    }

    public int getThrowNumber() {
        return throwNumber;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean isTriple() {
        return isTriple;
    }

    public int getPie() {
        return pie;
    }

    public String getId() {
        return id;
    }
}
