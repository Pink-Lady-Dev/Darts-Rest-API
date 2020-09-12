package com.pinkladydev.darts.player.mappers;

import com.pinkladydev.darts.player.Player;
import com.pinkladydev.darts.player.PlayerEntity;
import org.junit.jupiter.api.Test;

import static com.pinkladydev.darts.player.helpers.ChancePlayerEntity.getRandomPlayerEntity;
import static com.pinkladydev.darts.player.Player.aPlayerBuilder;
import static com.pinkladydev.darts.player.mappers.PlayerToPlayerEntityMapper.map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlayerToPlayerEntityMapperTest {

    @Test
    void map_shouldReturnPlayerEntityCorrespondingToPlayer() {
        PlayerEntity expectedPlayerEntity = getRandomPlayerEntity();

        Player player = aPlayerBuilder()
                .id(expectedPlayerEntity.getId())
                .username(expectedPlayerEntity.getUsername())
                .gameLog(expectedPlayerEntity.getGameLog())
                .leagues(expectedPlayerEntity.getLeagues())
                .build();

        assertThat(map(player)).isEqualToComparingFieldByField(expectedPlayerEntity);
    }
}
