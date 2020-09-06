package com.pinkladydev.darts.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(@Qualifier("PlayerMongo") PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void newPlayer (String username) {
        playerDao.insertPlayer(username);
    }

    public boolean doesPlayerExist(String username){
        return playerDao.getPlayer(username) != null;
    }
}
