package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GameUser{

    private final User user;
    private Hashtable<String, Integer> score;
    private List<DartThrow> darts;
    private Game.GAME_TYPE game_type;

    public GameUser(User user, Game.GAME_TYPE game_type) {
        this.user = user;
        this.score = new Hashtable<String, Integer>();
        this.darts = new ArrayList<DartThrow>();
        this.game_type = game_type;
    }


    /** SCORE UPDATERS **/

    public void updateScore(DartThrow dartThrow, boolean isRemove){
        switch (getGame_type()){
            case X01:
                updateX01(dartThrow, isRemove);
                break;
        }
    }

    public void updateX01(DartThrow dartThrow, boolean isRemove){
        int points = dartThrow.getPoints();
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

    public void addDart(DartThrow dartThrow){
        darts.add(dartThrow);
        updateScore(dartThrow, false);
    }

    public DartThrow removeDart(DartThrow dartThrow){
        darts.remove(dartThrow);
        return dartThrow;
    }

    public DartThrow removeDart(){
        DartThrow temp = darts.get(darts.size() - 1);
        darts.remove(darts.size() - 1);
        return temp;
    }

    /**  GETTERS  **/
    public Hashtable<String, Integer> getScore() {
        return score;
    }

    public Game.GAME_TYPE getGame_type() {
        return game_type;
    }

    public List<DartThrow> getDarts() {
        return darts;
    }

    @JsonIgnore
    // @GetMapping uses all serializes all getters, so this suppresses this from being returned
    public User getUser() {
        return user;
    }
}