package com.pinkladydev.darts.player;

import java.util.ArrayList;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.player.Player.aPlayerBuilder;
import static com.pinkladydev.darts.player.PlayerEntity.aPlayerEntityBuilder;

public class Helpers {

    public static PlayerEntity getRandomPlayerEntity(){
        return aPlayerEntityBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(15,20)))
                .username(getRandomAlphaNumericString(getRandomNumberBetween(15,20)))
                .leagues(generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(15,20)),
                        getRandomNumberBetween(0,10)))
                .gameLog(generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(15,20)),
                        getRandomNumberBetween(4,50)))
                .build();
    }

    public static PlayerEntity getRandomNewPlayerEntity(){
        return aPlayerEntityBuilder()
                .username(getRandomAlphaNumericString(getRandomNumberBetween(15,20)))
                .leagues(new ArrayList<>())
                .gameLog(new ArrayList<>())
                .build();
    }

    public static Player getRandomPlayer(){
        return aPlayerBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(15,20)))
                .username(getRandomAlphaNumericString(getRandomNumberBetween(15,20)))
                .leagues(generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(15,20)),
                        getRandomNumberBetween(0,10)))
                .gameLog(generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(15,20)),
                        getRandomNumberBetween(4,50)))
                .build();
    }
}
