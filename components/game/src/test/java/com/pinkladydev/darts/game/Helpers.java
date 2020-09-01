package com.pinkladydev.darts.game;

import com.pinkladydev.darts.player.Dart;
import com.pinkladydev.darts.player.GameType;

import java.util.List;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;

public class Helpers {

    public static Game randomGame(){
        final List<String> usernames = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,4));

        // Make this randomize gameType
        return randomX01(usernames);
    }

    public static Game randomGame(List<String> usernames){

        // Make this randomize gameType
        return randomX01(usernames);
    }

    public static Game randomX01(){
        final List<String> usernames = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,4));

        return Game.startX01(usernames, getRandomNumberBetween(3,10) * 100 + 1);
    }

    public static Game randomX01(List<String> usernames){
        return Game.startX01(usernames, getRandomNumberBetween(3,10) * 100 + 1);
    }

    public static Dart randomDart(){
        final Integer pie = getRandomNumberBetween(1,20);
        final Boolean isDouble = getRandomBoolean();
        final Boolean isTriple = getRandomBoolean();
        return new Dart(getRandomNumberBetween(0,2), pie, isDouble, isTriple);
    }
}
