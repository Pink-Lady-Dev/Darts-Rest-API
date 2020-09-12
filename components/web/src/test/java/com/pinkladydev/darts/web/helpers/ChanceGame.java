package com.pinkladydev.darts.web.helpers;

import com.pinkladydev.darts.game.Game;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GameType;

import java.util.List;
import java.util.UUID;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.Game.aGameBuilder;
import static java.util.stream.Collectors.toList;

public class ChanceGame {

    public static Game randomGame(){
        return  (getRandomBoolean() ? randomCricket() : randomX01()).build();
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

    public static Game.GameBuilder randomCricket(){
        final String gameId = UUID.randomUUID().toString();
        final List<GamePlayer> gamePlayers = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,4)).stream().map(player -> GamePlayer.StartCricket(gameId, player)).collect(toList());

        return aGameBuilder()
                .gamePlayers(gamePlayers)
                .id(gameId)
                .gameType(GameType.CRICKET);
    }
}
