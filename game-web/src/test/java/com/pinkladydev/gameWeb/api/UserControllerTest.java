package com.pinkladydev.gameWeb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkladydev.gameWeb.api.models.UserRequest;
import com.pinkladydev.gameWeb.helpers.ChanceUser;
import com.pinkladydev.gameWeb.model.User;
import com.pinkladydev.gameWeb.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.pinkladydev.gameWeb.exceptions.UserDataFailure.failureToSaveUserToMongo;
import static com.pinkladydev.gameWeb.helpers.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.gameWeb.helpers.Chance.getRandomNumberBetween;
import static com.pinkladydev.gameWeb.helpers.GenerateMany.generateListOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes=UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void insertUser_shouldReturnWithCreated_andCallInsertUser() throws Exception {
        final UserRequest userRequest = new UserRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,25)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,25))
        );
        final ArgumentCaptor<UserRequest> argument = ArgumentCaptor.forClass(UserRequest.class);

        this.mockMvc.perform(post("/user/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(userRequest)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).insertUser(argument.capture());
        assertEquals(userRequest.getUsername(), argument.getValue().getUsername());
        assertEquals(userRequest.getPassword(), argument.getValue().getPassword());
    }

    @Test
    void insertUser_shouldReturnWithServiceUnavailable_whenUserSaveFailureIsThrown() throws Exception {
        final UserRequest userRequest = new UserRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,25)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,25))
        );

        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,25));
        doThrow(failureToSaveUserToMongo(exceptionMessage)).when(userService).insertUser(any());

        this.mockMvc.perform(post("/user/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(userRequest)))
                .andExpect(status().isServiceUnavailable());
    }


    @Test
    void getUsers_shouldReturnWithOk_andReturnAllUsers() throws Exception {
        final UserRequest userRequest = new UserRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,25)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,25))
        );
        final List<User> users = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));

        when(userService.getAllUsers()).thenReturn(users);
        this.mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(users)));

        verify(userService, times(1)).getAllUsers();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
