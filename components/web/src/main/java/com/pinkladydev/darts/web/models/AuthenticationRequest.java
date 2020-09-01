package com.pinkladydev.darts.web.models;

public class AuthenticationRequest {

    // TODO try and make final
    private final String username;
    private final String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequest() {
        this.username = "";
        this.password = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
