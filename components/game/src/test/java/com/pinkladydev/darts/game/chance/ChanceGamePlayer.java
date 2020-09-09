package com.pinkladydev.darts.game.chance;

import com.pinkladydev.darts.game.GamePlayer;

import java.util.List;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.GamePlayer.StartCricket;
import static com.pinkladydev.darts.game.GamePlayer.StartX01;
import static com.pinkladydev.darts.game.chance.ChanceDart.getRandomDart;

public class ChanceGamePlayer {

    public static GamePlayer getRandomGamePlayer(){
        return getRandomBoolean() ? getX01RandomGamePlayer() : getCricketRandomGamePlayer();
    }

    public static GamePlayer getRandomGamePlayerWithDarts(){
        return getRandomBoolean() ? getX01RandomGamePlayerWithDarts() : getCricketRandomGamePlayerWithDarts();
    }

    public static GamePlayer getX01RandomGamePlayer(){
        return StartX01(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                301
        );
    }

    public static GamePlayer getX01RandomGamePlayerWithDarts(){
        GamePlayer tempGamePlayer = StartX01(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                301
        );
        generateListOf(ChanceDart::getRandomDart, getRandomNumberBetween(1,5))
                .forEach(tempGamePlayer::addDart);
        return tempGamePlayer;
    }

    public static GamePlayer getCricketRandomGamePlayer(){
        return StartCricket(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20))
        );
    }

    public static GamePlayer getCricketRandomGamePlayerWithDarts(){
        GamePlayer tempGamePlayer = StartCricket(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20))
        );
        generateListOf(() -> getRandomDart(List.of(15,16,17,18,19,20,25)), getRandomNumberBetween(1,5))
                .forEach(tempGamePlayer::addDart);
        return tempGamePlayer;
    }
}
