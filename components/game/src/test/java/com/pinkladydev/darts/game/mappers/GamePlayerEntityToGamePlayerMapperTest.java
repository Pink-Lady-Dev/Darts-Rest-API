package com.pinkladydev.darts.game.mappers;

import com.pinkladydev.darts.chance.Chance;
import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GamePlayerEntity;
import com.pinkladydev.darts.game.Helpers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;
import static com.pinkladydev.darts.game.Helpers.getRandomGamePlayer;
import static com.pinkladydev.darts.game.mappers.GamePlayerEntityToGamePlayerMapper.map;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamePlayerEntityToGamePlayerMapperTest {

    @Test
    void map_shouldReturnGamePlayerCorrespondingToGamePlayerEntity() {
        final List<Dart> dartList = generateListOf(Helpers::getRandomDart, Chance.getRandomNumberBetween(0,8));
        // Use Map.of() in +1.9
        final List<Map<String, String>> dartMapList = dartList.stream().map(dart -> {
            HashMap<String, String> map = new HashMap<String, String>(){};
            map.put("id", dart.getId());
            map.put("throwNumber", dart.getThrowNumber().toString());
            map.put("pie", dart.getPie().toString());
            map.put("isDouble", dart.isDouble().toString());
            map.put("isTriple", dart.isTriple().toString());
            return map;
        }).collect(toList());

        final GamePlayer expectedGamePlayer = getRandomGamePlayer(dartList);
        final GamePlayerEntity gamePlayerEntity = aGamePlayerEntityBuilder()
                .gameId(expectedGamePlayer.getGameId())
                .id(expectedGamePlayer.getGameId())
                .username(expectedGamePlayer.getUsername())
                .gameType(expectedGamePlayer.getGameType())
                .wins(new ArrayList<>())
                .losses(new ArrayList<>())
                .score(expectedGamePlayer.getScore())
                .darts(dartMapList)
                .build();

        final GamePlayer actualGamePlayer = map(gamePlayerEntity);
        final List<Integer> actualPoints = actualGamePlayer.getDarts().stream().map(Dart::getPoints).collect(toList());

        assertThat(actualGamePlayer)
                .isEqualToIgnoringGivenFields(expectedGamePlayer, "darts");
        expectedGamePlayer.getDarts().forEach(dart -> {
            assertTrue(actualPoints.contains(dart.getPoints()));
        });
    }
}
