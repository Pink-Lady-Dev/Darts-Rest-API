package com.pinkladydev.darts.game.mappers;

import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GamePlayerEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pinkladydev.darts.game.GamePlayer.aGamePlayerBuilder;
import static com.pinkladydev.darts.game.chance.Helpers.getRandomGamePlayerEntityBuilder;
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
                .build();

        final GamePlayerEntity actualGamePlayerEntity = map(tempGamePlayer);
        assertThat(actualGamePlayerEntity)
                .isEqualToIgnoringGivenFields(expectedGamePlayerEntity,  "id","wins","losses");
    }
}
