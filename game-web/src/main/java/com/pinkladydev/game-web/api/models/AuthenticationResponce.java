package com.pinkladydev.DartsRestAPI.api.models;

import com.pinkladydev.DartsRestAPI.model.User;

public class AuthenticationResponce {

    private final String jwt;
    private final User user;

    public AuthenticationResponce(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public User getUser(){
        return user;
    }
}
