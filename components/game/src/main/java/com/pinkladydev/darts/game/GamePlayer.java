package com.pinkladydev.darts.game;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.game.exceptions.InvalidDartException.InvalidCricketDartException;

@Builder
public class GamePlayer {

    private final String username;
    private final String gameId;
    private final GameType gameType;
    private final Map<String, Integer> score;
    private final List<Dart> darts;
    private final List<String> wins;
    private final List<String> losses;

    private static GamePlayer newGamePlayer(String gameId, String username, GameType gameType) {
        return aGamePlayerBuilder()
                .username(username)
                .gameId(gameId)
                .gameType(gameType)
                .score(new HashMap<>())
                .darts(new ArrayList<>())
                .wins(new ArrayList<>())
                .losses(new ArrayList<>())
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
        player.score.put("25", 0);
        return player;
    }

    /** SCORE UPDATERS **/
    public Dart addDart(Dart dart){
        if (GameType.X01 == gameType) return addX01(dart);
        if (GameType.CRICKET == gameType) return addCricket(dart);
        return null;
    }

    public void removeDart(){
        if (GameType.X01 == gameType){
            removeX01();
        } else if (GameType.CRICKET == gameType){
          removeCricket();
        }
    }

    private Dart addX01(final Dart dart){
        int provisionalScore = score.get("score") - dart.getPoints();

        DartResponseType dartResponseType = dart.getThrowNumber() == 2 ? DartResponseType.ROUND_OVER : DartResponseType.NEXT_THROW;
        if (provisionalScore == 0) dartResponseType = DartResponseType.GAME_OVER;
        if (provisionalScore < 0) {
            dartResponseType = DartResponseType.BUST;
            provisionalScore = 0;
        };

        dart.setDartResponseType(dartResponseType);
        darts.add(dart);
        score.put("score", provisionalScore);

        return dart;
    }

    private void removeX01(){
        final Dart removed = darts.remove(darts.size() - 1);
        score.put("score", score.get("score") + removed.getPoints());
    }

    private Dart addCricket(final Dart dart){
        if (!List.of(15,16,17,18,19,20,25).contains(dart.getPie())){
            throw InvalidCricketDartException(dart.getPie().toString());
        }

        DartResponseType dartResponseType = dart.getThrowNumber() == 2 ? DartResponseType.ROUND_OVER : DartResponseType.NEXT_THROW;
        if (score.values().stream().noneMatch(pieValue -> pieValue < 3)) dartResponseType = DartResponseType.GAME_OVER;

        dart.setDartResponseType(dartResponseType);
        darts.add(dart);
        score.put(dart.getPie().toString(), score.get(dart.getPie().toString()) + 1);

        return dart;
    }

    private void removeCricket(){
        final Dart removed = darts.remove(darts.size() - 1);
        score.put(removed.getPie().toString(), Math.max(score.get(removed.getPie().toString()) - 1 , 0));
    }

    /** Winners and Losers **/
    public void winGame(List<String> loserUsernames){
        this.wins.add(username);
        this.losses.addAll(loserUsernames);
    }

    public void loseGame(String loserUsername){
        this.losses.add(loserUsername);
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
