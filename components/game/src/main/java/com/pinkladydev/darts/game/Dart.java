package com.pinkladydev.darts.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Dart {

    // Throw information
    private final String  id; // Identifier for throw
    private final Integer throwNumber;
    private final Integer points; // Calculated score
    private final Integer pie; // Actual number hit
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
            String id,
            Integer  throwNumber,
            Integer pie,
            boolean isDouble,
            boolean isTriple) {

        // Dart scoring
        this.id = id;
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

    public Dart(
            Integer  throwNumber,
            Integer pie,
            boolean isDouble,
            boolean isTriple) {

        this(UUID.randomUUID().toString(), throwNumber, pie, isDouble, isTriple);
    }

    public Dart(
            @JsonProperty("throwNumber") int  throwNumber,
            @JsonProperty("points") int points,
            @JsonProperty("pie") int pie,
            @JsonProperty("isDouble") boolean isDouble,
            @JsonProperty("isTriple") boolean isTriple) {

        // Dart scoring
        this.id = "id";
        this.throwNumber = throwNumber;
        this.points = points;
        this.pie = pie;
        this.isDouble = isDouble;
        this.isTriple = isTriple;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getThrowNumber() {
        return throwNumber;
    }

    public Boolean isDouble() {
        return isDouble;
    }

    public Boolean isTriple() {
        return isTriple;
    }

    public Integer getPie() {
        return pie;
    }

    public String getId() {
        return id;
    }
}
