package com.pinkladydev.gameWeb.service;

import com.pinkladydev.gameWeb.api.models.UserRequest;
import com.pinkladydev.gameWeb.dao.UserDao;
import com.pinkladydev.gameWeb.exceptions.UserDataFailure;
import com.pinkladydev.gameWeb.helpers.ChanceUser;
import com.pinkladydev.gameWeb.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static com.pinkladydev.gameWeb.exceptions.UserDataFailure.failureToSaveUserToMongo;
import static com.pinkladydev.gameWeb.helpers.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.gameWeb.helpers.Chance.getRandomNumberBetween;
import static com.pinkladydev.gameWeb.helpers.ChanceUser.randomUser;
import static com.pinkladydev.gameWeb.helpers.ChanceUser.randomUserRequest;
import static com.pinkladydev.gameWeb.helpers.GenerateMany.generateListOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes= UserService.class)
class UserServiceTest {

    @MockBean
    @Qualifier("Mongo")
    private UserDao userDao;


    private UserService userService;

    @BeforeEach
    public void setup(){
        this.userService = new UserService(userDao);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetailsFromUsername() {
        final User user = randomUser();

        when(userDao.getUser(user.getUsername())).thenReturn(user);
        final UserDetails actual = userService.loadUserByUsername(user.getUsername());

        verify(userDao, times(1)).getUser(user.getUsername());
        assertThat(actual).isEqualTo(user);
    }

    @Test
    void insertUser_shouldCallInsertUser() {
        final UserRequest userRequest = randomUserRequest();

        doNothing().when(userDao).insertUser(eq(userRequest));

        userService.insertUser(userRequest);
        verify(userDao, times(1)).insertUser(userRequest);
    }


    @Test
    void insertUser_shouldThrowUserDataFailure_whenInsertUserThrowsUserDataFailure() {
        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,50));
        final UserRequest userRequest = randomUserRequest();

        doThrow(failureToSaveUserToMongo(exceptionMessage)).when(userDao).insertUser(eq(userRequest));

        assertThrows(UserDataFailure.class, () -> userService.insertUser(userRequest), "");
    }

    @Test
    void getAllUsers() {
        final List<User> users = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));

        when(userDao.getAllUsers()).thenReturn(users);
        final List<User> actual = userService.getAllUsers();

        verify(userDao, times(1)).getAllUsers();
        assertThat(actual).isEqualTo(users);
    }

    @Test
    void getUser_shouldReturnCorrectUser() {
        final User user = randomUser();

        when(userDao.getUser(user.getId())).thenReturn(user);
        final User actual = userService.getUser(user.getId());

        verify(userDao, times(1)).getUser(user.getId());
        assertThat(actual).isEqualTo(user);
    }
}
