package com.pinkladydev.darts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.Game;
import com.pinkladydev.darts.game.GameService;
import com.pinkladydev.darts.user.User;
import com.pinkladydev.darts.web.helpers.ChanceUser;
import com.pinkladydev.darts.web.models.GameRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.web.Helpers.randomDart;
import static com.pinkladydev.darts.web.Helpers.randomGame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= GameController.class)
@AutoConfigureMockMvc(addFilters = false)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void createGame_shouldReturnWithCreated_andCallCreateGame() throws Exception {
        final List<String> usernames = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,2))
                .stream().map(User::getUsername).collect(Collectors.toList());
        final GameRequest gameRequest = new GameRequest(
                usernames,
                getRandomAlphaNumericString(getRandomNumberBetween(2,6)));

        final String gameRequestString = "{\"users\":"
                + stringArray(gameRequest.getUsers().stream().toArray(String[]::new))
                + ",\"gameType\":\"" + gameRequest.getGameType()
                + "\"}";

        final ArgumentCaptor<GameRequest> argument = ArgumentCaptor.forClass(GameRequest.class);
        this.mockMvc.perform(post("/game")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gameRequestString))
                .andExpect(status().isCreated());

        verify(gameService, times(1)).createGame(gameRequest.getUsers(), gameRequest.getGameType());
    }

    @Test
    void getGameData_shouldReturnWithOk_andReturnGameDataFromGameService() throws Exception {
        final Game game = randomGame();

        when(gameService.getGameData(game.getId())).thenReturn(game);
        this.mockMvc.perform(get("/game/" + game.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(game)));

        verify(gameService, times(1)).getGameData(game.getId());
    }

    @Test
    void postGameDataToWeb_shouldReturnWithOk_andCallNotifyWebClient() throws Exception {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final String webId = getRandomNumberBetween(0,9999).toString();

        this.mockMvc.perform(post("/game/" + gameId).contentType(MediaType.APPLICATION_JSON).content("{\"webId\":\"" + webId + "\"}"))
                .andExpect(status().isOk());

        verify(gameService, times(1)).notifyWebClient(gameId, webId);
    }

    @Test
    void getGameUsers_shouldReturnWithOk_andReturnGameUsers() throws Exception {
        final Game game = randomGame();

        when(gameService.getGamePlayers(game.getId())).thenReturn(game.getGamePlayers());

        this.mockMvc.perform(get("/game/" + game.getId() + "/user/"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(game.getGamePlayers())));

        verify(gameService, times(1)).getGamePlayers(game.getId());
    }

    @Test
    void getUserGameData_shouldReturnWithOk_andReturnUserDateForASpecificUser() throws Exception {
        final Game game = randomGame();
        final String playerName = game.getGamePlayers().get(0).getUsername();
        when(gameService.getGameData(game.getId())).thenReturn(game);

        this.mockMvc.perform(get("/game/" + game.getId() + "/user/" + playerName))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(game.getGamePlayers().get(0))));
    }

    @Test
    void addUserGameDart_shouldReturnWithOk_andCallAddDartForTheCorrectUser() throws Exception {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final String userId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));

        final Dart dart = randomDart();

        final String dartString = "{\"throwNumber\":" + dart.getThrowNumber()
                + ",\"pie\":" + dart.getPie()
                + ",\"double\":" + dart.isDouble()
                + ",\"triple\":" + dart.isTriple()
                + "}";
        final ArgumentCaptor<Dart> argument = ArgumentCaptor.forClass(Dart.class);

        this.mockMvc.perform(post("/game/" + gameId + "/user/" + userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(dartString))
                .andExpect(status().isOk());


        verify(gameService,times(1)).addDart(eq(gameId),eq(userId),argument.capture());

        assertEquals(dart.getThrowNumber(), argument.getValue().getThrowNumber());
        assertEquals(dart.getPie(), argument.getValue().getPie());
        assertEquals(dart.isDouble(), argument.getValue().isDouble());
        assertEquals(dart.isTriple(), argument.getValue().isTriple());
    }

    @Test
    void removeUserGameDart() throws Exception {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final String userId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));


        final ArgumentCaptor<Dart> argument = ArgumentCaptor.forClass(Dart.class);

        this.mockMvc.perform(delete("/game/" + gameId + "/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(gameService,times(1)).removeLastDart(eq(gameId),eq(userId));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String stringArray(String[] strs){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < strs.length; i++){
            stringBuilder.append("\"");
            stringBuilder.append(strs[i]);
            stringBuilder.append("\"");
            if (i + 1 != strs.length){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}


