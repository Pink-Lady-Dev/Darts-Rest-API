package com.pinkladydev.DartsRestAPI.service;

import com.pinkladydev.DartsRestAPI.dao.GameDao;
import com.pinkladydev.DartsRestAPI.dao.UserDao;
import com.pinkladydev.DartsRestAPI.model.Dart;
import com.pinkladydev.DartsRestAPI.model.Game;
import com.pinkladydev.DartsRestAPI.model.GameHelper;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameDao gameDao;
    private final UserDao userDao;


    @Autowired
    public GameService(@Qualifier("FakeGameDao") GameDao gameDao,@Qualifier("FakeDao") UserDao userDao){
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    public Game getGameData (String gameID) {
        return gameDao.getGameData(gameID);
    }

    public void createGame(GameHelper game) {

        User[] users = game.getUsers().stream().map(x -> userDao.getUser(x)).toArray(User[]::new);

        gameDao.createGame(new Game(game.getId(), users, game.getGameType()));
    }

    public List<User> getUsersInGame(String gameId) {
        return getGameData(gameId).getUsers();
    }

    public void addDart(String gameId, String userId, Dart dart) {
        getGameData(gameId).getGameUser(userId).addX01(dart);
    }

    public Dart removeDart(String gameId, String userId, Dart dart) {
        return getGameData(gameId).getGameUser(userId).removeX01(dart);
    }


}
