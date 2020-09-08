package com.pinkladydev.darts.game.mappers;

import com.pinkladydev.darts.chance.Chance;
import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GamePlayerEntity;
import com.pinkladydev.darts.game.Helpers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.GamePlayer.aGamePlayerBuilder;
import static com.pinkladydev.darts.game.Helpers.getRandomGamePlayerEntityBuilder;
import static com.pinkladydev.darts.game.mappers.GamePlayerToGamePlayerEntityMapper.map;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GamePlayerToGamePlayerEntityMapperTest {

    @Test
    void map_shouldReturnGamePlayerEntityCorrespondingToGamePlayer() {
        final List<Map<String, String>> dartMapList = generateListOf(Helpers::getRandomDartMap, Chance.getRandomNumberBetween(0,8));
        final List<Dart> dartList = dartMapList.stream().map(dartMap -> new Dart(
                    dartMap.get("id"),
                    parseInt(dartMap.get("throwNumber")),
                    parseInt(dartMap.get("pie")),
                    parseBoolean(dartMap.get("isDouble")),
                    parseBoolean(dartMap.get("isTriple"))))
                .collect(toList());

        final GamePlayerEntity expectedGamePlayerEntity = getRandomGamePlayerEntityBuilder()
                .darts(dartMapList)
                .build();
        final GamePlayer tempGamePlayer = aGamePlayerBuilder()
                .gameId(expectedGamePlayerEntity.getGameId())
                .username(expectedGamePlayerEntity.getUsername())
                .gameType(expectedGamePlayerEntity.getGameType())
                .score(expectedGamePlayerEntity.getScore())
                .darts(dartList)
                .build();

        final GamePlayerEntity actualGamePlayerEntity = map(tempGamePlayer);
        assertThat(actualGamePlayerEntity)
                .isEqualToIgnoringGivenFields(expectedGamePlayerEntity,  "id","wins","losses");
    }
}