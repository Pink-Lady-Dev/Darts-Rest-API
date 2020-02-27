package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GameHelper {

    private final String  id;
    private final List<String> users;
    private final String gameType;

    public GameHelper(
            @JsonProperty("id") String id,
            @JsonProperty("users") List<String> users,
            @JsonProperty("gameType") String gameType)
    {
        this.id = id;
        this.users = users;
        this.gameType = gameType;
    }

    public String getId() {
        return id;
    }

    public List<String> getUsers() {
        return users;
    }

    public String getGameType() {
        return gameType;
    }
}
