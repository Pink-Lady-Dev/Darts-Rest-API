package com.pinkladydev.darts.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinkladydev.darts.user.User;
import com.pinkladydev.darts.user.UserService;
import com.pinkladydev.darts.web.helpers.ChanceUser;
import com.pinkladydev.darts.web.models.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.user.UserDataFailure.failureToSaveUserToMongo;
import static com.pinkladydev.darts.web.helpers.ChanceUser.randomUserRequest;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void insertUser_shouldReturnWithCreated_andCallInsertUser() throws Exception {
        final UserRequest userRequest = randomUserRequest();

        this.mockMvc.perform(post("/user/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(userRequest)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).insertUser(userRequest.getUsername(), userRequest.getPassword());
    }

    @Test
    void insertUser_shouldReturnWithServiceUnavailable_whenUserSaveFailureIsThrown() throws Exception {
        final UserRequest userRequest = randomUserRequest();

        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,25));
        doThrow(failureToSaveUserToMongo(exceptionMessage)).when(userService)
                .insertUser(userRequest.getUsername(),userRequest.getPassword());

        this.mockMvc.perform(post("/user/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(userRequest)))
                .andExpect(status().isServiceUnavailable());
    }


    @Test
    void getUsers_shouldReturnWithOk_andReturnAllUsers() throws Exception {
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
