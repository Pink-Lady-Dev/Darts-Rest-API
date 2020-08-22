package com.pinkladydev.DartsRestAPI.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

    private final String username;
    private final String password;

    public UserRequest(
            @JsonProperty("name") String username,
            @JsonProperty("password") String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
