package com.pinkladydev.darts.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePlayer {

    private final String username;
    private GameType gameType;
    private final Map<String, Integer> score;
    private final List<Dart> darts;

    private GamePlayer(String username) {
        this.username = username;

        this.score = new HashMap<>();
        this.darts = new ArrayList<>();
    }

    /**   Game Initializers   **/
    public static GamePlayer StartX01(String username, int score){
        GamePlayer player = new GamePlayer(username);
        player.gameType = GameType.X01;
        player.score.put("score", score);
        return player;
    }

    public static GamePlayer StartCricket(String username){
        GamePlayer player = new GamePlayer(username);
        player.gameType = GameType.CRICKET;
        player.score.put("20", 0);
        player.score.put("19", 0);
        player.score.put("18", 0);
        player.score.put("17", 0);
        player.score.put("16", 0);
        player.score.put("15", 0);
        player.score.put("bull", 0);
        return player;
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

    /** Getters **/

    public String getUsername() {
        return username;
    }

    public List<Dart> getDarts() {
        return darts;
    }

    public GameType getGameType() {
        return gameType;
    }

    public Map<String, Integer> getScore() {
        return score;
    }
}
