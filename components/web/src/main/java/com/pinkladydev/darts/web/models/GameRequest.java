package com.pinkladydev.darts.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GameRequest {

    private final List<String> users;
    private final String gameType;

    public GameRequest(
            @JsonProperty("users") List<String> users,
            @JsonProperty("gameType") String gameType)
    {
        this.users = users;
        this.gameType = gameType;
    }

    public List<String> getUsers() {
        return users;
    }

    public String getGameType() {
        return gameType;
    }
}
