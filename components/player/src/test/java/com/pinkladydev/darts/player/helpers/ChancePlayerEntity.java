package com.pinkladydev.darts.player.helpers;

import com.pinkladydev.darts.player.PlayerEntity;

import java.util.ArrayList;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.player.PlayerEntity.aPlayerEntityBuilder;

public class ChancePlayerEntity {

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
}
