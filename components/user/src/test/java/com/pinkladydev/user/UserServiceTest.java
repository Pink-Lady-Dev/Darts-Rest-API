package com.pinkladydev.user;

import com.pinkladydev.user.helpers.ChanceUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static com.pinkladydev.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.chance.GenerateMany.generateListOf;
import static com.pinkladydev.user.UserDataFailure.failureToSaveUserToMongo;
import static com.pinkladydev.user.helpers.ChanceUser.randomUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserService.class})
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
        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String password = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        doNothing().when(userDao).insertUser(username, password);

        userService.insertUser(username,password);
        verify(userDao, times(1)).insertUser(username, password);
    }


    @Test
    void insertUser_shouldThrowUserDataFailure_whenInsertUserThrowsUserDataFailure() {
        final String exceptionMessage = getRandomAlphaNumericString(getRandomNumberBetween(5,50));

        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String password = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        doThrow(failureToSaveUserToMongo(exceptionMessage)).when(userDao).insertUser(username, password);

        assertThrows(UserDataFailure.class, () -> userService.insertUser(username,password), "");
    }

    @Test
    void getAllUsers() {
        final List<User> users = generateListOf(ChanceUser::randomUser, getRandomNumberBetween(2,4));

        when(userDao.getAllUsers()).thenReturn(users);
        final List<User> actual = userService.getAllUsers();

        verify(userDao, times(1)).getAllUsers();
        Assertions.assertThat(actual).isEqualTo(users);
    }

    @Test
    void getUser_shouldReturnCorrectUser() {
        final User user = ChanceUser.randomUser();

        when(userDao.getUser(user.getId())).thenReturn(user);
        final User actual = userService.getUser(user.getId());

        verify(userDao, times(1)).getUser(user.getId());
        assertThat(actual).isEqualTo(user);
    }
}
