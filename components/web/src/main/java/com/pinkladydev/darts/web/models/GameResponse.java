package com.pinkladydev.darts.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameResponse {

    private final String gameId;

    public GameResponse(
            @JsonProperty("gameId") final String gameId)
    {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
