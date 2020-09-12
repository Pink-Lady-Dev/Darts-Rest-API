package com.pinkladydev.darts.player;

import com.pinkladydev.darts.chance.Chance;
import com.pinkladydev.darts.player.helpers.ChancePlayer;
import com.pinkladydev.darts.player.mappers.PlayerToPlayerEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.player.helpers.ChancePlayer.getRandomPlayer;
import static com.pinkladydev.darts.player.helpers.ChancePlayerEntity.getRandomNewPlayerEntity;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PlayerDataService.class})
class PlayerDataServiceTest {

    @MockBean
    private PlayerRepository playerRepository;

    private PlayerDataService playerDataService;

    @BeforeEach
    public void setup(){
        this.playerDataService = new PlayerDataService(playerRepository);
    }

    @Test
    void insertPlayer_shouldCallSaveFromPlayerRepository() {
        final PlayerEntity playerEntity = getRandomNewPlayerEntity();

        when(playerRepository.save(any())).thenReturn(playerEntity);
        playerDataService.insertPlayer(playerEntity.getUsername());

        ArgumentCaptor<PlayerEntity> argumentCaptor = ArgumentCaptor.forClass(PlayerEntity.class);
        verify(playerRepository, times(1)).save(argumentCaptor.capture());

        assertThat(playerEntity).isEqualToComparingFieldByField(argumentCaptor.getValue());
    }

    @Test
    void updatePlayer() {
        final Player player = getRandomPlayer();
        final PlayerEntity playerEntity = PlayerToPlayerEntityMapper.map(player);

        when(playerRepository.save(any())).thenReturn(playerEntity);
        playerDataService.updatePlayer(player);

        ArgumentCaptor<PlayerEntity> argumentCaptor = ArgumentCaptor.forClass(PlayerEntity.class);
        verify(playerRepository, times(1)).save(argumentCaptor.capture());

        assertThat(playerEntity).isEqualToComparingFieldByField(argumentCaptor.getValue());
    }

    @Test
    void getAllPlayers() {
        final List<Player> players = generateListOf(ChancePlayer::getRandomPlayer, Chance.getRandomNumberBetween(5, 20));
        final List<PlayerEntity> playerEntities = players.stream().map(PlayerToPlayerEntityMapper::map).collect(toList());

        when(playerRepository.findAll()).thenReturn(playerEntities);
        List<Player> actualPlayers = playerDataService.getAllPlayers();

        verify(playerRepository, times(1)).findAll();

        final List<String> actualUsernames = actualPlayers.stream().map(Player::getUsername).collect(toList());
        players.forEach(player -> {
            assertTrue(actualUsernames.contains(player.getUsername()));
        });
    }

    @Test
    void getPlayer() {
        final Player player = getRandomPlayer();
        final PlayerEntity playerEntity = PlayerToPlayerEntityMapper.map(player);

        when(playerRepository.findByUsername(player.getUsername())).thenReturn(playerEntity);
        Player actualPlayer = playerDataService.getPlayer(player.getUsername());

        verify(playerRepository, times(1)).findByUsername(player.getUsername());

        assertThat(player).isEqualToComparingFieldByField(actualPlayer);
    }
}
