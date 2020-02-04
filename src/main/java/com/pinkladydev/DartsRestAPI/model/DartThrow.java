package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;
//import org.graalvm.compiler.replacements.SnippetTemplate;

import java.util.UUID;

public class DartThrow {

    // Identifying Information
    private final User player;

    // Game Information
    // TODO : private final Game game;

    // Throw information
    private final String  id; // Identifier for throw
    private final int throwNumber;
    private final int points; // Calculated score
    private final int pie; // Actual number hit
    private final boolean isDouble;
    private final boolean isTriple;

    public DartThrow(DartThrow dartThrow) {
        this.player = dartThrow.player;
        this.id = dartThrow.id;
        this.throwNumber = dartThrow.throwNumber;
        this.points = dartThrow.points;
        this.pie = dartThrow.pie;
        this.isDouble = dartThrow.isDouble;
        this.isTriple = dartThrow.isTriple;
    }

    public DartThrow(
            @JsonProperty("player") User player,
            @JsonProperty("throwNumber") int  throwNumber,
            @JsonProperty("pie") int pie,
            @JsonProperty("double") boolean isDouble,
            @JsonProperty("triple") boolean isTriple) {

        // Player Info
        this.player = player;

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

    public User getPlayer() {
        return player;
    }

    public String getId() {
        return id;
    }

    public int getThrowNumber() {
        return throwNumber;
    }

    public int getPoints() {
        return points;
    }

    public int getPie() {
        return pie;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public boolean isTriple() {
        return isTriple;
    }

}
