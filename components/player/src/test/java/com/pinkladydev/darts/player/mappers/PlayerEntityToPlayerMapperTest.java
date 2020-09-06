package com.pinkladydev.darts.player.mappers;

import com.pinkladydev.darts.player.Player;
import com.pinkladydev.darts.player.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.pinkladydev.darts.player.Helpers.getRandomPlayer;
import static com.pinkladydev.darts.player.PlayerEntity.aPlayerEntityBuilder;
import static com.pinkladydev.darts.player.mappers.PlayerEntityToPlayerMapper.map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {PlayerEntityToPlayerMapper.class})
class PlayerEntityToPlayerMapperTest {

    @Test
    void map_shouldReturnPlayerCorrespondingToPlayerEntity() {
        Player expectedPlayer = getRandomPlayer();

        PlayerEntity playerEntity = aPlayerEntityBuilder()
                .id(expectedPlayer.getId())
                .username(expectedPlayer.getUsername())
                .gameLog(expectedPlayer.getGameLog())
                .leagues(expectedPlayer.getLeagues())
                .build();

        assertThat(map(playerEntity)).isEqualToComparingFieldByField(expectedPlayer);
    }
}
