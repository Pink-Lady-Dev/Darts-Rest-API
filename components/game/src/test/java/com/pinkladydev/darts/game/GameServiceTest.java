package com.pinkladydev.darts.game;

import com.pinkladydev.darts.player.PlayerService;
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
import static com.pinkladydev.darts.game.chance.ChanceDart.getRandomDart;
import static com.pinkladydev.darts.game.chance.Helpers.randomGame;
import static com.pinkladydev.darts.game.chance.Helpers.randomX01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private PlayerService playerService;

    @MockBean
    private SimpMessagingTemplate template;

    private GameService gameService;

    @BeforeEach
    public void setup(){
        this.gameService = new GameService(gameDao, playerService, template);
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

        when(playerService.doesPlayerExist(any(String.class))).thenReturn(true);
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

    @Test
    void addDart_shouldAddDartToPlayer_andSavePlayerToDao_andSendMessageTemplate_whenGameIsNotOver() {
        gameService.setWebId(getRandomNumberBetween(1,9999).toString());

        final Dart dart = getRandomDart();
        final String socketAddress = "/topic/notification/" + gameService.getWebId();

        final Game game = randomGame();
        final GamePlayer gamePlayer = game.getGamePlayers().get(getRandomNumberBetween(0,game.getGamePlayers().size() - 1));

        when(gameDao.getGamePlayer(game.getId(), gamePlayer.getUsername())).thenReturn(gamePlayer);
        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());

        gameService.addDart(game.getId(), gamePlayer.getUsername(), dart.getThrowNumber(), dart.getPie(), dart.isDouble(), dart.isTriple());

        verify(gameDao, times(1)).save(eq(gamePlayer));
        verify(template, times(1)).convertAndSend(eq(socketAddress), any(DartNotification.class));
    }

    @Test
    void addDart_shouldUpdateWinsAndLosses_andSaveAllPlayersToDao_whenGameIsOver() {
        gameService.setWebId(getRandomNumberBetween(1,9999).toString());

        final Dart dart = getRandomDart();
        final String socketAddress = "/topic/notification/" + gameService.getWebId();

        Game game = randomGame();
        while (game.getGamePlayers().size() == 1){
            game = randomGame();
        }
        final GamePlayer gamePlayer = game.getGamePlayers().get(getRandomNumberBetween(0,game.getGamePlayers().size() - 1));
        gamePlayer.getScore().put("score", dart.getPoints());


        when(gameDao.getGamePlayer(game.getId(), gamePlayer.getUsername())).thenReturn(gamePlayer);
        when(gameDao.getGamePlayers(game.getId())).thenReturn(game.getGamePlayers());
        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());

        gameService.addDart(game.getId(), gamePlayer.getUsername(), dart.getThrowNumber(), dart.getPie(), dart.isDouble(), dart.isTriple());

        assertTrue(gamePlayer.getWins().contains(game.getGamePlayers().get(1).getUsername()));
        assertTrue(game.getGamePlayers().get(1).getLosses().contains(gamePlayer.getUsername()));
        verify(gameDao, times(game.getGamePlayers().size())).save(any(GamePlayer.class));
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
            final Dart tempDart = getRandomDart();

            expectedScore.put("score", expectedScore.get("score") - tempDart.getPoints());
            gameService.addDart(game.getId(), gamePlayer.getUsername(), tempDart.getThrowNumber(), tempDart.getPie(), tempDart.isDouble(),tempDart.isTriple());
        }

        final Dart dart = getRandomDart();
        gameService.addDart(game.getId(), gamePlayer.getUsername(), dart.getThrowNumber(), dart.getPie(), dart.isDouble(),dart.isTriple());
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
