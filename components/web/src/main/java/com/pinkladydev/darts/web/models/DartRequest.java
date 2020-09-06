package com.pinkladydev.darts.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DartRequest {

    private final int throwNumber;
    private final int pie;
    private final boolean isDouble;
    private final boolean isTriple;

    public DartRequest(
            @JsonProperty("throwNumber") int  throwNumber,
            @JsonProperty("pie") int pie,
            @JsonProperty("double") boolean isDouble,
            @JsonProperty("triple") boolean isTriple) {

        this.throwNumber = throwNumber;
        this.pie = pie;
        this.isDouble = isDouble;
        this.isTriple = isTriple;
    }

    public int getThrowNumber() {
        return throwNumber;
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
