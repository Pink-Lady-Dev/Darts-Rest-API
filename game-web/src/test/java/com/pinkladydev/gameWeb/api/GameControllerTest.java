package com.pinkladydev.gameWeb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkladydev.gameWeb.api.models.GameRequest;
import com.pinkladydev.gameWeb.helpers.ChanceUser;
import com.pinkladydev.gameWeb.model.Dart;
import com.pinkladydev.gameWeb.model.Game;
import com.pinkladydev.gameWeb.model.User;
import com.pinkladydev.gameWeb.service.GameService;
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

import static com.pinkladydev.gameWeb.helpers.Chance.*;
import static com.pinkladydev.gameWeb.helpers.GenerateMany.generateListOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes=GameController.class)
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
                getRandomAlphaNumericString(getRandomNumberBetween(15,20)),
                usernames,
                getRandomAlphaNumericString(getRandomNumberBetween(2,6)));
        final String gameRequestString = "{\"id\":\"" + gameRequest.getId()
                + "\",\"users\":" + stringArray(gameRequest.getUsers().stream().toArray(String[]::new))
                + ",\"gameType\":\"" + gameRequest.getGameType()
                + "\"}";

        final ArgumentCaptor<GameRequest> argument = ArgumentCaptor.forClass(GameRequest.class);
        this.mockMvc.perform(post("/game")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gameRequestString))
                .andExpect(status().isCreated());

        verify(gameService, times(1)).createGame(argument.capture());

        assertEquals(gameRequest.getId(),argument.getValue().getId());
        assertEquals(gameRequest.getUsers(), argument.getValue().getUsers());
        assertEquals(gameRequest.getGameType(),argument.getValue().getGameType());
    }

    @Test
    void getGameData_shouldReturnWithOk_andReturnGameDataFromGameService() throws Exception {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));

        final Game game = new Game(gameId, userList, "X01");
        when(gameService.getGameData(gameId)).thenReturn(game);
        this.mockMvc.perform(get("/game/" + gameId))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(game)));

        verify(gameService, times(1)).getGameData(gameId);
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
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));

        when(gameService.getUsersInGame(gameId)).thenReturn(userList);

        this.mockMvc.perform(get("/game/" + gameId + "/user/"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userList)));

        verify(gameService, times(1)).getUsersInGame(gameId);
    }

    @Test
    void getUserGameData_shouldReturnWithOk_andReturnUserDateForASpecificUser() throws Exception {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final List<User> userList = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));
        final User user = userList.get(0);

        final Game game = new Game(gameId, userList, "X01");
        when(gameService.getGameData(gameId)).thenReturn(game);

        this.mockMvc.perform(get("/game/" + gameId + "/user/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(user)));
    }

    @Test
    void addUserGameDart_shouldReturnWithOk_andCallAddDartForTheCorrectUser() throws Exception {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final String userId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));

        final Integer throwNumber = getRandomNumberBetween(0,2);
        final Integer pie = getRandomNumberBetween(1,20);
        final Boolean isDouble = getRandomBoolean();
        final Boolean isTriple = getRandomBoolean();
        final Dart dart = new Dart(throwNumber,pie,isDouble,isTriple);

        final String dartString = "{\"throwNumber\":" + throwNumber.toString()
                + ",\"pie\":" + pie.toString()
                + ",\"double\":" + isDouble.toString()
                + ",\"triple\":" + isTriple.toString()
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
    void removeUserGameDart() {
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


