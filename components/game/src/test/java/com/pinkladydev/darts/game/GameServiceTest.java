package com.pinkladydev.darts.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.List;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.Helpers.randomDart;
import static com.pinkladydev.darts.game.Helpers.randomGame;
import static com.pinkladydev.darts.game.Helpers.randomX01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {GameService.class})
class GameServiceTest {

    @MockBean
    @Qualifier("MongoGame")
    private GameDao gameDao;

    @MockBean
    private SimpMessagingTemplate template;

    private GameService gameService;

    @BeforeEach
    public void setup(){
        this.gameService = new GameService(gameDao, template);
    }

    @Test
    void getGameData_shouldReturnTotalGameFromDao() {
        final Game game = randomGame();
        final String gameId = game.getId();

        when(gameDao.getTotalGame(gameId)).thenReturn(game);

        final Game actual = gameService.getGameData(gameId);

        assertThat(actual).isEqualTo(game);
    }

    @Test
    void createGame_shouldCreateGameOfX01ForEachUser_whenXO1IsGameType() {
        final List<String> gamePlayerUsernames = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(1,4));

        doNothing().when(gameDao).save(any());

        gameService.createGame(gamePlayerUsernames, "X01");

        verify(gameDao, times(gamePlayerUsernames.size() )).save(any(GamePlayer.class));
    }

    @Test
    void getGamePlayers_shouldReturnListOfGamePlayersInGame() {
        final Game game = randomGame();

        when(gameDao.getGamePlayers(game.getId())).thenReturn(game.getGamePlayers());

        final List<GamePlayer> actual = gameService.getGamePlayers(game.getId());

        assertThat(actual).isEqualTo(game.getGamePlayers());
    }

    // Needs test in game to verify scoring and these should just make sure it calls it
    // Agnostic of Game
    @Test
    void addDart_forX01Game_shouldUpdateUserScore_andSendMessageTemplate() {
        gameService.setWebId(getRandomNumberBetween(1,9999).toString());

        final Dart dart = randomDart();
        final Integer startingScore = (getRandomNumberBetween(0,7) * 100) + 301;
        final String socketAddress = "/topic/notification/" + gameService.getWebId();

        final Game game = randomX01(startingScore).build();
        final GamePlayer gamePlayer = game.getGamePlayers().get(getRandomNumberBetween(0,game.getGamePlayers().size() - 1));


        final int dartThrows = getRandomNumberBetween(2,7);
        final HashMap<String, Integer> expectedScore = new HashMap<>();

        when(gameDao.getGamePlayer(game.getId(), gamePlayer.getUsername())).thenReturn(gamePlayer);
        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());

        expectedScore.put("score", startingScore);

        for (int dNumber = 0; dNumber < dartThrows; dNumber++){
            final Dart tempDart = randomDart();

            expectedScore.put("score", expectedScore.get("score") - tempDart.getPoints());
            gameService.addDart(game.getId(), gamePlayer.getUsername(), tempDart);
        }

        assertThat(gamePlayer.getScore()).isEqualTo(expectedScore);
        verify(template, times(dartThrows)).convertAndSend(eq(socketAddress), any(DartNotification.class));
    }

    @Test
    void removeLastDart_forX01Game_shouldUpdateUserScore_andSendMessageTemplate() {

        gameService.setWebId(getRandomNumberBetween(1,9999).toString());

        final int startingScore = (getRandomNumberBetween(0,7) * 100) + 301;
        final String socketAddress = "/topic/notification/" + gameService.getWebId();

        final Game game = randomX01(startingScore).build();
        final GamePlayer gamePlayer = game.getGamePlayers().get(getRandomNumberBetween(0,game.getGamePlayers().size() - 1));
        final HashMap<String, Integer> expectedScore = new HashMap<>();

        when(gameDao.getGamePlayer(game.getId(), gamePlayer.getUsername())).thenReturn(gamePlayer);
        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());

        expectedScore.put("score", startingScore);
        for (int dNumber = 0; dNumber < getRandomNumberBetween(2,6); dNumber++){
            final Dart tempDart = randomDart();

            expectedScore.put("score", expectedScore.get("score") - tempDart.getPoints());
            gameService.addDart(game.getId(), gamePlayer.getUsername(), tempDart);
        }

        final Dart dart = randomDart();
        gameService.addDart(game.getId(), gamePlayer.getUsername(), dart);
        gameService.removeLastDart(game.getId(), gamePlayer.getUsername());

        assertThat(gamePlayer.getScore()).isEqualTo(expectedScore);
        verify(template, times(1)).convertAndSend(eq(socketAddress), any(RemovedDartNotification.class));
    }

    @Test
    void notifyWebClient_shouldUpdateWebId_andSendGameMetaNotification() {
        final String webId = getRandomNumberBetween(1,9999).toString();
        final String socketAddress = "/topic/notification/" + webId;

        final Game game = randomGame();
        final GameMetaNotification expectedGameMetaNotification = new GameMetaNotification(game.getGamePlayers(), "X01");

        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());
        when(gameService.getGameData(game.getId())).thenReturn(game);

        final ArgumentCaptor<GameMetaNotification> actualGameMetaNotification = ArgumentCaptor.forClass(GameMetaNotification.class);

        gameService.notifyWebClient(game.getId(), webId);

        verify(template, times(1)).convertAndSend(eq(socketAddress), actualGameMetaNotification.capture());
        assertThat(gameService.getWebId()).isEqualTo(webId);
        assertEquals(game.getGamePlayers(), actualGameMetaNotification.getValue().getPlayers());
        assertEquals(expectedGameMetaNotification.getGameType(), actualGameMetaNotification.getValue().getGameType());
    }
}
