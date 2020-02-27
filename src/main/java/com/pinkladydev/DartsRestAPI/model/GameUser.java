package com.pinkladydev.DartsRestAPI.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUser{

    private User user;
    private HashMap<String, Integer> score;
    private List<Dart> darts;
    private Game.GAME_TYPE gameType;


    public GameUser(User user, Game.GAME_TYPE gameType) {
        this.user = user;
        this.score = new HashMap<>();
        this.darts = new ArrayList<>();

        this.gameType = gameType;

    }

    /** SCORE UPDATERS **/

    public void updateScore(Dart dart, boolean isRemove){
        switch (getGameType()){
            case X01:
                updateX01(dart, isRemove);
                break;
        }
    }

    public void updateX01(Dart dart, boolean isRemove){
        int points = dart.getPoints();
        if (isRemove) {
            points *= -1;
        }
        score.put("points", score.get("points") - points);
    }


    /**  SETTERS ( AND MANIPULATORS )  **/
    public void addScoreKey (String key, int value) {
        // TODO do validation
        this.score.put(key, value);
    }

    public void addDart(Dart dart){
        darts.add(dart);
        updateScore(dart, false);
    }

    public Dart removeDart(Dart dart){
        darts.remove(dart);
        return dart;
    }

    public Dart removeDart(){
        Dart temp = darts.get(darts.size() - 1);
        darts.remove(darts.size() - 1);
        return temp;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**  GETTERS  **/
    public Map<String, Integer> getScore() {
        return score;
    }

    public Game.GAME_TYPE getGameType() {
        return gameType;
    }

    public List<Dart> getDarts() {
        return darts;
    }

    // @GetMapping uses all serializes all getters, so this suppresses this from being returned
    public User getUser() {
        return user;
    }
}