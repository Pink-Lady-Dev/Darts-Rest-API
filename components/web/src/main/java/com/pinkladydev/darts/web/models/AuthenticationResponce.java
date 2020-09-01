package com.pinkladydev.darts.web.models;

import com.pinkladydev.darts.user.User;

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
