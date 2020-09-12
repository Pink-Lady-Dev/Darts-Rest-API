package com.pinkladydev.darts.game;

import com.pinkladydev.darts.game.mappers.GamePlayerToGamePlayerEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.pinkladydev.darts.game.HashGenerator.generateGamePlayerHash;
import static com.pinkladydev.darts.game.chance.ChanceGame.randomGame;
import static com.pinkladydev.darts.game.chance.ChanceGamePlayer.getRandomGamePlayer;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GameDataAccessService.class})
class GameDataAccessServiceTest {

    @MockBean
    private GameRepository gameRepository;

    private GameDataAccessService gameDataAccessService;

    @BeforeEach
    public void setup(){
        this.gameDataAccessService = new GameDataAccessService(gameRepository);
    }

    @Test
    void getGamePlayer_shouldReturnGamePlayerForGivenGameIdAndUsername() {
        final GamePlayer gamePlayer = getRandomGamePlayer();

        when(gameRepository.findGamePlayerEntityById(generateGamePlayerHash(gamePlayer.getUsername(), gamePlayer.getGameId()))).thenReturn(GamePlayerToGamePlayerEntityMapper.map(gamePlayer));

        final GamePlayer actualGamePlayer = gameDataAccessService.getGamePlayer(gamePlayer.getGameId(),gamePlayer.getUsername());

        assertThat(gamePlayer).isEqualToComparingFieldByField(actualGamePlayer);
    }

    @Test
    void getGamePlayers_shouldReturnListOfGamePlayersForGivenGameId() {
        final Game game = randomGame();
        final List<GamePlayer> expectedGamePlayers = game.getGamePlayers();
        final List<String> expectedUsernames = expectedGamePlayers.stream().map(GamePlayer::getUsername).collect(toList());

        when(gameRepository.findAllByGameId(anyString())).thenReturn(expectedGamePlayers.stream().map(GamePlayerToGamePlayerEntityMapper::map).collect(toList()));

        final List<GamePlayer> actualGamePlayers = gameDataAccessService.getGamePlayers(game.getId());

        actualGamePlayers.forEach(gamePlayer -> assertTrue(expectedUsernames.contains(gamePlayer.getUsername())));
    }

    @Test
    void getTotalGame_shouldReturnTotalGameForGivenGameId() {
        final Game expectedGame = randomGame();
        final List<String> expectedUsernames = expectedGame.getGamePlayers().stream().map(GamePlayer::getUsername).collect(toList());

        when(gameRepository.findAllByGameId(anyString()))
                .thenReturn(expectedGame.getGamePlayers().stream().map(GamePlayerToGamePlayerEntityMapper::map).collect(toList()));

        final Game actualGame = gameDataAccessService.getTotalGame(expectedGame.getId());

        assertThat(actualGame).isEqualToIgnoringGivenFields(expectedGame, "gamePlayers");
        actualGame.getGamePlayers().forEach(gamePlayer -> assertTrue(expectedUsernames.contains(gamePlayer.getUsername())));
    }

    @Test
    void save_shouldSaveGamePlayerToRepository() {
        final GamePlayer expectedGamePlayer = getRandomGamePlayer();
        final GamePlayerEntity expectedGamePlayerEntity = GamePlayerToGamePlayerEntityMapper.map(expectedGamePlayer);

        when(gameRepository.save(any())).thenReturn(expectedGamePlayerEntity);

        gameDataAccessService.save(expectedGamePlayer);

        ArgumentCaptor<GamePlayerEntity> gamePlayerEntityArgumentCaptor = ArgumentCaptor.forClass(GamePlayerEntity.class);

        verify(gameRepository, times(1)).save(gamePlayerEntityArgumentCaptor.capture());
        assertThat(gamePlayerEntityArgumentCaptor.getValue()).isEqualToComparingFieldByField(expectedGamePlayerEntity);
    }
}
