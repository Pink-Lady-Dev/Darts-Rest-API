package com.pinkladydev.darts.game.chance;

import com.pinkladydev.darts.game.Game;
import com.pinkladydev.darts.game.GamePlayerEntity;
import com.pinkladydev.darts.game.GameType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.Game.aGameBuilder;
import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;
import static com.pinkladydev.darts.game.chance.ChanceDart.getRandomDartMap;
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
        final List<com.pinkladydev.darts.game.GamePlayer> gamePlayers = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,4)).stream().map(player -> com.pinkladydev.darts.game.GamePlayer.StartX01(gameId, player, score)).collect(toList());

        return aGameBuilder()
                .gamePlayers(gamePlayers)
                .id(gameId)
                .gameType(GameType.X01);
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
