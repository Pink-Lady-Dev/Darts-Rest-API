package com.pinkladydev.darts.game;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class GamePlayer {

    private final String username;
    private final String gameId;
    private final GameType gameType;
    private final Map<String, Integer> score;
    private final List<Dart> darts;

    private static GamePlayer newGamePlayer(String gameId, String username, GameType gameType) {
        return aGamePlayerBuilder()
                .username(username)
                .gameId(gameId)
                .gameType(gameType)
                .score(new HashMap<>())
                .darts(new ArrayList<>())
                .build();
    }

    /**   Game Initializers   **/
    public static GamePlayer StartX01(String gameId, String username, int score){
        GamePlayer player = newGamePlayer(gameId, username,GameType.X01);
        player.score.put("score", score);
        return player;
    }

    public static GamePlayer StartCricket(String gameId, String username){
        GamePlayer player = newGamePlayer(gameId, username, GameType.CRICKET);
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

    public String getGameId() {
        return gameId;
    }

    public static GamePlayerBuilder aGamePlayerBuilder(){
        return builder();
    }
}
