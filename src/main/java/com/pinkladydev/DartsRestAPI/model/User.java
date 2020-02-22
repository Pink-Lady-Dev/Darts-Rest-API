package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    private final String  id;
    private final String name;

    // TODO add player dart stats

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name)
    {
        this.id = id.toString();
        this.name = name;
    }

    public User(@JsonProperty("id") String id,
                @JsonProperty("name") String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
