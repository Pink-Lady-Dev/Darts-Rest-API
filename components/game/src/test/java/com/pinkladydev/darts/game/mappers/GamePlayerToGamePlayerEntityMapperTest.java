package com.pinkladydev.darts.game.mappers;

import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GamePlayerEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.GamePlayer.aGamePlayerBuilder;
import static com.pinkladydev.darts.game.chance.ChanceGamePlayerEntity.getRandomGamePlayerEntityBuilder;
import static com.pinkladydev.darts.game.mappers.GamePlayerToGamePlayerEntityMapper.map;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GamePlayerToGamePlayerEntityMapperTest {

    @Test
    void map_shouldReturnGamePlayerEntityCorrespondingToGamePlayer() {
        final GamePlayerEntity expectedGamePlayerEntity = getRandomGamePlayerEntityBuilder()
                .build();
        if (getRandomBoolean()){
            expectedGamePlayerEntity.getLosses().add(getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
        }
        expectedGamePlayerEntity.getLosses().addAll(generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(0,3)));

        final List<Dart> expectedDartList = expectedGamePlayerEntity.getDarts().stream().map(dartMap -> new Dart(
                dartMap.get("id"),
                parseInt(dartMap.get("throwNumber")),
                parseInt(dartMap.get("pie")),
                parseBoolean(dartMap.get("isDouble")),
                parseBoolean(dartMap.get("isTriple"))))
                .collect(toList());

        final GamePlayer tempGamePlayer = aGamePlayerBuilder()
                .gameId(expectedGamePlayerEntity.getGameId())
                .username(expectedGamePlayerEntity.getUsername())
                .gameType(expectedGamePlayerEntity.getGameType())
                .score(expectedGamePlayerEntity.getScore())
                .darts(expectedDartList)
                .wins(expectedGamePlayerEntity.getWins())
                .losses(expectedGamePlayerEntity.getLosses())
                .build();

        final GamePlayerEntity actualGamePlayerEntity = map(tempGamePlayer);
        assertThat(actualGamePlayerEntity)
                .isEqualToIgnoringGivenFields(expectedGamePlayerEntity,  "id");
    }
}
