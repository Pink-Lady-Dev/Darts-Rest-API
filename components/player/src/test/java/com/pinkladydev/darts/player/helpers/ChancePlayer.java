package com.pinkladydev.darts.player.helpers;

import com.pinkladydev.darts.player.Player;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.player.Player.aPlayerBuilder;

public class ChancePlayer {

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
