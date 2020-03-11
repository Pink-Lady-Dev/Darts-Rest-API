package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class User implements UserDetails {

    private final String  id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    // TODO add player dart stats
    // we want to store wins and losses in each type of game
    // as well as head to head matchups in each type of game

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("name") String username,
                @JsonProperty("password") String password)
    {
        this.id = id.toString();
        this.username = username;
        this.password = password;

        List<GrantedAuthority> tempAuthorities = new ArrayList<>();

        tempAuthorities.add(new SimpleGrantedAuthority("USER"));
        tempAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        this.authorities = tempAuthorities;
    }

    public User(@JsonProperty("id") String id,
                @JsonProperty("name") String username,
                @JsonProperty("password") String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;

        List<GrantedAuthority> tempAuthorities = new ArrayList<>();

        tempAuthorities.add(new SimpleGrantedAuthority("USER"));
        tempAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        this.authorities = tempAuthorities;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    // @JsonIgnore
    // @GetMapping uses all serializes all getters, so this suppresses this from being returned
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}
