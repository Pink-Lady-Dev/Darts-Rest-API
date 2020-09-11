package com.pinkladydev.darts.game.mappers;

import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GamePlayerEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;
import static com.pinkladydev.darts.game.chance.ChanceGamePlayer.getRandomGamePlayerWithDarts;
import static com.pinkladydev.darts.game.mappers.GamePlayerEntityToGamePlayerMapper.map;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamePlayerEntityToGamePlayerMapperTest {

    @Test
    void map_shouldReturnGamePlayerCorrespondingToGamePlayerEntity() {
        final GamePlayer expectedGamePlayer = getRandomGamePlayerWithDarts();
        if (getRandomBoolean()){
            expectedGamePlayer.loseGame(getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
        }
        expectedGamePlayer.winGame(generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,3)));

        final List<Map<String, String>> expectedDartMapList = expectedGamePlayer.getDarts().stream().map(dart ->
                Map.of(
                    "id", dart.getId(),
                    "throwNumber", dart.getThrowNumber().toString(),
                    "pie", dart.getPie().toString(),
                    "isDouble", dart.isDouble().toString(),
                    "isTriple", dart.isTriple().toString()))
                .collect(toList());

        final GamePlayerEntity gamePlayerEntity = aGamePlayerEntityBuilder()
                .gameId(expectedGamePlayer.getGameId())
                .id(expectedGamePlayer.getGameId())
                .username(expectedGamePlayer.getUsername())
                .gameType(expectedGamePlayer.getGameType())
                .wins(expectedGamePlayer.getWins())
                .losses(expectedGamePlayer.getLosses())
                .score(expectedGamePlayer.getScore())
                .darts(expectedDartMapList)
                .build();

        final GamePlayer actualGamePlayer = map(gamePlayerEntity);
        final List<Integer> actualPoints = actualGamePlayer.getDarts().stream().map(Dart::getPoints).collect(toList());

        assertThat(actualGamePlayer)
                .isEqualToIgnoringGivenFields(expectedGamePlayer, "darts");
        expectedGamePlayer.getDarts().forEach(dart -> assertTrue(actualPoints.contains(dart.getPoints())));
    }
}
