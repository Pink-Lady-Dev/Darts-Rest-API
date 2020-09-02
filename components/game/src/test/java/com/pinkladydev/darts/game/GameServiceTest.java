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
    @Qualifier("FakeGameDao")
    private GameDao gameDao;

    @MockBean
    private SimpMessagingTemplate template;

    private GameService gameService;

    @BeforeEach
    public void setup(){
        this.gameService = new GameService(gameDao, template);
    }

    @Test
    void getGameData_shouldReturnGameDataFromDao() {
        final Game game = randomGame();
        final String gameId = game.getId();

        when(gameDao.getGameData(gameId)).thenReturn(game);

        final Game actual = gameService.getGameData(gameId);

        assertThat(actual).isEqualTo(game);
    }

    @Test
    void createGame_shouldCallCreateGameOfX01_withGameInstanceGeneratedFromRequest() {
        final List<String> gamePlayerUsernames = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomNumberBetween(2,4));

        doNothing().when(gameDao).createGame(any());

        // TODO write a function that generates a random game... names, type, score
        final Game expectedGame = randomGame(gamePlayerUsernames);
        final ArgumentCaptor<Game> actualArgument = ArgumentCaptor.forClass(Game.class);

        gameService.createGame(expectedGame.getId(), gamePlayerUsernames, "X01");

        verify(gameDao, times(1)).createGame(actualArgument.capture());
        assertEquals(expectedGame.getId(), actualArgument.getValue().getId());
        assertEquals(expectedGame.getGamePlayers(), actualArgument.getValue().getGamePlayers());
        expectedGame.getGamePlayers().forEach(user -> {
            assertEquals(301, actualArgument.getValue().getGameUser(user.getUsername()).getScore().get("score"));
        });

    }

    @Test
    void getUsersInGame_shouldReturnListOfUsersInGame() {
        final Game game = randomGame();

        when(gameDao.getGameData(game.getId())).thenReturn(game);

        final List<GamePlayer> actual = gameService.getUsersInGame(game.getId());

        assertThat(actual).isEqualTo(game.getGamePlayers());
    }

    @Test
    void addDart_forX01Game_shouldUpdateUserScore_andSendMessageTemplate() {
        gameService.setWebId(getRandomNumberBetween(1,9999).toString());

        final Dart dart = randomDart();
        final Integer startingScore = (getRandomNumberBetween(0,7) * 100) + 301;
        final String socketAddress = "/topic/notification/" + gameService.getWebId();

        final Game game = randomX01();
        final GamePlayer gamePlayer = game.getGamePlayers().get(getRandomNumberBetween(0,game.getGamePlayers().size() - 1));


        final HashMap<String, Integer> expectedScore = new HashMap<>();
        expectedScore.put("score", startingScore - scoreDart(dart.getPie(), dart.isDouble(), dart.isTriple()));

        when(gameDao.getGameData(game.getId())).thenReturn(game);
        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());

        final ArgumentCaptor<Dart> actualDart = ArgumentCaptor.forClass(Dart.class);

        gameService.addDart(game.getId(), gamePlayer.getUsername(), dart);

        assertThat(gamePlayer.getScore()).isEqualTo(expectedScore);
        verify(template, times(1)).convertAndSend(eq(socketAddress), actualDart.capture());
    }

    @Test
    void removeDart() {
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

    private Integer scoreDart(Integer pie, Boolean isDouble, Boolean isTriple){
        if (isDouble == isTriple){
            return pie;
        } else if (isDouble){
            return pie * 2;
        } else if (isTriple){
            return pie * 3;
        }
        return pie;
    }
}
