package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {

    private final String  id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    private HashMap<String, Integer> score;
    private List<Dart> darts;
    private Game.GAME_TYPE gameType;

    // TODO add player dart stats
    // we want to store wins and losses in each type of game
    // as well as head to head matchups in each type of game

    // Will use this to actually set users??
    public User(
                @JsonProperty("name") String username,
                @JsonProperty("password") String password)
    {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;

        List<GrantedAuthority> tempAuthorities = new ArrayList<>();

        tempAuthorities.add(new SimpleGrantedAuthority("USER"));
        tempAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        this.authorities = tempAuthorities;

        this.darts = new ArrayList<>();

        StartX01(301);
    }

    public User(
            String username,
            String password,
            String id)
    {
        this.id = id;
        this.username = username;
        this.password = password;

        List<GrantedAuthority> tempAuthorities = new ArrayList<>();

        tempAuthorities.add(new SimpleGrantedAuthority("USER"));
        tempAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        this.authorities = tempAuthorities;

        this.darts = new ArrayList<>();

        StartX01(301);
    }

    /**   Game Initializers   **/
    public void StartX01(int score){
        this.score = new HashMap<>();
        this.score.put("score", score);

        gameType = Game.GAME_TYPE.X01;
    }

    /** SCORE UPDATERS **/

    public void addX01(Dart dart){
        darts.add(dart);
        int points = dart.getPoints();
        score.put("score", score.get("score") - points);

        // TODO do checks for game over
    }

    public Dart removeX01(Dart dart){
        int points = dart.getPoints();
        score.put("score", score.get("score") + points);
        darts.remove(dart);
        return dart;
    }


    /**  SETTERS ( AND MANIPULATORS )  **/
    public Dart removeDart(){
        Dart temp = darts.get(darts.size() - 1);
        darts.remove(darts.size() - 1);
        return temp;
    }

    /**  GETTERS  **/

    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    // @GetMapping uses all serializes all getters, so this suppresses this from being returned
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
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

    public Map<String, Integer> getScore() {
        return score;
    }

    public Game.GAME_TYPE getGameType() {
        return gameType;
    }

    public List<Dart> getDarts() {
        return darts;
    }











}
