package com.pinkladydev.darts.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.player.helpers.ChancePlayer.getRandomPlayer;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest(classes = {PlayerService.class})
class PlayerServiceTest {

    @MockBean
    @Qualifier("PlayerMongo")
    private PlayerDao playerDao;

    private PlayerService playerService;

    @BeforeEach
    public void setup(){
        this.playerService = new PlayerService(playerDao);
    }

    @Test
    void newPlayer_shouldCallInsertPlayerOnThePlayerDao() {
        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        playerService.newPlayer(username);

        verify(playerDao, times(1)).insertPlayer(username);
    }

    @Test
    void addGameLog_shouldGetPlayer_andUpdateTheGameLog() {
        final Player player = getRandomPlayer();
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));

        when(playerDao.getPlayer(player.getUsername())).thenReturn(player);

        playerService.addGameLog(player.getUsername(), gameId);

        assertTrue(player.getGameLog().contains(gameId));
    }

    @Test
    void doesPlayerExist_shouldReturnFalseWhenAUserDoesNotExist() {
        final Player player = getRandomPlayer();

        when(playerDao.getPlayer(player.getUsername())).thenReturn(null);

        playerService.newPlayer(player.getUsername());

        assertFalse(playerService.doesPlayerExist(player.getUsername()));
    }

    @Test
    void doesPlayerExist_shouldReturnTrueWhenAUserDoesExist() {
        final Player player = getRandomPlayer();

        when(playerDao.getPlayer(player.getUsername())).thenReturn(player);

        playerService.newPlayer(player.getUsername());

        assertTrue(playerService.doesPlayerExist(player.getUsername()));
    }


}
