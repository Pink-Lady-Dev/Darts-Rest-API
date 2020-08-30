package com.pinkladydev.web.api.models;

import com.pinkladydev.user.User;

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
