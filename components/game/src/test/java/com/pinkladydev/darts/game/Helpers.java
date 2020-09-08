package com.pinkladydev.darts.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.Game.aGameBuilder;
import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;
import static java.util.stream.Collectors.toList;

public class Helpers {

    // TODO - break out into own files - per return type
    public static Game randomGame(){
        final List<String> usernames = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,4));

        // Make this randomize gameType
        return randomX01().build();
    }

    public static Game randomGame(List<String> usernames){

        // Make this randomize gameType
        return randomX01().build();
    }
    public static Game.GameBuilder randomX01(){
        final int score = getRandomNumberBetween(3,10) * 100 + 1;

        return randomX01(score);
    }

    public static Game.GameBuilder randomX01(final int score){
        final String gameId = UUID.randomUUID().toString();
        final List<GamePlayer> gamePlayers = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,4)).stream().map(player -> GamePlayer.StartX01(gameId, player, score)).collect(toList());

        return aGameBuilder()
                .gamePlayers(gamePlayers)
                .id(gameId)
                .gameType(GameType.X01);
    }

    public static Dart randomDart(){
        final Integer pie = getRandomNumberBetween(1,20);
        final Boolean isDouble = getRandomBoolean();
        final Boolean isTriple = getRandomBoolean();
        return new Dart(getRandomNumberBetween(0,2), pie, isDouble, isTriple);
    }

    public static GamePlayer getRandomGamePlayer(){
        return GamePlayer.StartX01(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                301
        );
    }

    public static GamePlayer getRandomGamePlayerWithDarts(){
        GamePlayer tempGamePlayer = GamePlayer.StartX01(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                301
        );
        generateListOf(Helpers::getRandomDart, getRandomNumberBetween(1,8))
                .forEach(tempGamePlayer::addDart);
        return tempGamePlayer;
    }

    public static Map<String, String> getRandomDartMap(){
        final Boolean isTriple = getRandomBoolean();
        //{ "throwNumber" : "0", "pie" : "12", "did" : "08eadb76-98ed-46ae-a85e-c5fdea097536", "isTriple" : "true", "points" : "36", "isDouble" : "false" }
        final Map<String,String> dart = new HashMap<>();
        dart.put("throwNumber",getRandomNumberBetween(0,2).toString());
        dart.put("pie",getRandomNumberBetween(1,20).toString());
        dart.put("id", UUID.randomUUID().toString());
        dart.put("isTriple", isTriple.toString());
        dart.put("isDouble", isTriple ? "false" : getRandomBoolean().toString());

        return dart;
    }

    public static Dart getRandomDart(){
        return new Dart(
                UUID.randomUUID().toString(),
                getRandomNumberBetween(0,2),
                getRandomNumberBetween(1,20),
                getRandomBoolean(),
                getRandomBoolean());
    }

    public static GamePlayerEntity getRandomGamePlayerEntity(){
        return getRandomGamePlayerEntityBuilder().build();
    }

    public static GamePlayerEntity.GamePlayerEntityBuilder getRandomGamePlayerEntityBuilder(){

        final Map<String,Integer> scores = new HashMap<>();
        scores.put("score",301);
        return aGamePlayerEntityBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .gameId(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .losses(generateListOf(() -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)), getRandomNumberBetween(0,4)))
                .wins(generateListOf(() -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)), getRandomNumberBetween(0,4)))
                .gameType(GameType.X01)
                .score(scores)
                .darts(generateListOf(() -> getRandomDartMap(), getRandomNumberBetween(0, 12)));
    }
}
