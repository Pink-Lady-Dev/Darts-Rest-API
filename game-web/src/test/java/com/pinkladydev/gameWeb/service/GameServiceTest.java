package com.pinkladydev.gameWeb.service;

import com.pinkladydev.gameWeb.api.models.GameRequest;
import com.pinkladydev.gameWeb.dao.GameDao;
import com.pinkladydev.gameWeb.helpers.ChanceUser;
import com.pinkladydev.gameWeb.model.Game;
import com.pinkladydev.gameWeb.model.GameMetaNotification;
import com.pinkladydev.user.Dart;
import com.pinkladydev.user.User;
import com.pinkladydev.user.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.pinkladydev.chance.Chance.*;
import static com.pinkladydev.chance.GenerateMany.generateListOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GameService.class})
class GameServiceTest {

    @MockBean
    @Qualifier("FakeGameDao")
    private GameDao gameDao;

    @MockBean
    @Qualifier("Mongo")
    private UserDao userDao;

    @MockBean
    private SimpMessagingTemplate template;

    private GameService gameService;

    @BeforeEach
    public void setup(){
        this.gameService = new GameService(gameDao, userDao, template);
    }

    @Test
    void getGameData_shouldReturnGameDataFromDao() {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));
        final Game game = new Game(gameId, userList, "X01");

        when(gameDao.getGameData(gameId)).thenReturn(game);

        final Game actual = gameService.getGameData(gameId);

        assertThat(actual).isEqualTo(game);
    }

    @Test
    void createGame_shouldCallCreateGameOfX01_withGameInstanceGeneratedFromRequest() {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));

        userList.forEach(user -> when(userDao.getUser(user.getId())).thenReturn(user));
        doNothing().when(gameDao).createGame(any());

        final GameRequest gameRequest = new GameRequest(gameId,
                userList.stream().map(User::getId).collect(Collectors.toList()),
                "X01");

        final Game expectedGame = new Game(gameId, userList, "X01");
        final ArgumentCaptor<Game> actualArgument = ArgumentCaptor.forClass(Game.class);

        gameService.createGame(gameRequest);

        verify(gameDao, times(1)).createGame(actualArgument.capture());
        assertEquals(expectedGame.getId(), actualArgument.getValue().getId());
        assertEquals(expectedGame.getUsers(), actualArgument.getValue().getUsers());
        expectedGame.getUsers().forEach(user -> {
            assertEquals(301, actualArgument.getValue().getGameUser(user.getId()).getScore().get("score"));
        });

    }

    @Test
    void getUsersInGame_shouldReturnListOfUsersInGame() {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));
        final Game game = new Game(gameId, userList, "X01");

        when(gameDao.getGameData(gameId)).thenReturn(game);

        final List<User> actual = gameService.getUsersInGame(gameId);

        assertThat(actual).isEqualTo(game.getUsers());
    }

    @Test
    void addDart_forX01Game_shouldUpdateUserScore_andSendMessageTemplate() {
        gameService.setWebId(getRandomNumberBetween(1,9999).toString());

        final Integer pie = getRandomNumberBetween(1,20);
        final Integer startingScore = (getRandomNumberBetween(0,7) * 100) + 301;
        final Boolean isDouble = getRandomBoolean();
        final Boolean isTriple = getRandomBoolean();
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final String socketAddress = "/topic/notification/" + gameService.getWebId();
        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));

        final Game game = new Game(gameId, userList, "X01");
        final Dart dart = new Dart(getRandomNumberBetween(0,2), pie, isDouble, isTriple);
        final User user = userList.get(getRandomNumberBetween(0,userList.size() - 1));

        user.StartX01(startingScore);

        final HashMap<String, Integer> expectedScore = new HashMap<>();
        expectedScore.put("score", startingScore - scoreDart(pie, isDouble, isTriple));

        when(gameDao.getGameData(gameId)).thenReturn(game);
        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());

        final ArgumentCaptor<Dart> actualDart = ArgumentCaptor.forClass(Dart.class);

        gameService.addDart(gameId, user.getId(), dart);

        assertThat(user.getScore()).isEqualTo(expectedScore);
        verify(template, times(1)).convertAndSend(eq(socketAddress), actualDart.capture());
    }

    @Test
    void removeDart() {
    }

    @Test
    void notifyWebClient_shouldUpdateWebId_andSendGameMetaNotification() {
        final String webId = getRandomNumberBetween(1,9999).toString();
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final String socketAddress = "/topic/notification/" + webId;

        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));
        final Game game = new Game(gameId, userList, "X01");
        final GameMetaNotification expectedGameMetaNotification = new GameMetaNotification(userList, "X01");

        doNothing().when(template).convertAndSend(eq(socketAddress), (Object) any());
        when(gameService.getGameData(gameId)).thenReturn(game);

        final ArgumentCaptor<GameMetaNotification> actualGameMetaNotification = ArgumentCaptor.forClass(GameMetaNotification.class);

        gameService.notifyWebClient(gameId, webId);

        verify(template, times(1)).convertAndSend(eq(socketAddress), actualGameMetaNotification.capture());
        assertThat(gameService.getWebId()).isEqualTo(webId);
        assertEquals(game.getUsers(), actualGameMetaNotification.getValue().getPlayers());
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
