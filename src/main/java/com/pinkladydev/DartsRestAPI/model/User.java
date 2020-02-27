package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    private final String  id;
    private final String name;
    private final String password;

    // TODO add player dart stats

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("password") String password)
    {
        this.id = id.toString();
        this.name = name;
        this.password = password;
    }

    public User(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("password") String password)
    {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    // @GetMapping uses all serializes all getters, so this suppresses this from being returned
    public String getPassword() {
       return password;
    }
}
