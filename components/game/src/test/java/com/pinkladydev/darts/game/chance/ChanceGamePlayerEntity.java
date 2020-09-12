package com.pinkladydev.darts.game.chance;

import com.pinkladydev.darts.game.GamePlayerEntity;
import com.pinkladydev.darts.game.GameType;

import java.util.HashMap;
import java.util.Map;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;
import static com.pinkladydev.darts.game.chance.ChanceDart.getRandomDartMap;

public class ChanceGamePlayerEntity {

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
