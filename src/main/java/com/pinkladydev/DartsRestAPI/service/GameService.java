package com.pinkladydev.DartsRestAPI.service;

import com.pinkladydev.DartsRestAPI.dao.GameDao;
import com.pinkladydev.DartsRestAPI.dao.UserDao;
import com.pinkladydev.DartsRestAPI.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameDao gameDao;
    private final UserDao userDao;

    private String webId;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    public GameService(@Qualifier("FakeGameDao") GameDao gameDao,@Qualifier("FakeDao") UserDao userDao){
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.webId = null;
    }

    public Game getGameData (String gameID) {
        return gameDao.getGameData(gameID);
    }

    public void createGame(GameHelper game) {

        User[] users = game.getUsers().stream().map(userDao::getUser).toArray(User[]::new);

        gameDao.createGame(new Game(game.getId(), users, game.getGameType()));

    }

    public List<User> getUsersInGame(String gameId) {
        return getGameData(gameId).getUsers();
    }

    public void addDart(String gameId, String userId, Dart dart) {
        getGameData(gameId).getGameUser(userId).addX01(dart);

        User user  = getUsersInGame(gameId).stream().filter(x -> x.getId().equals(userId)).findFirst().orElseThrow(RuntimeException::new);
        template.convertAndSend("/topic/notification/" + this.webId, new DartNotification(dart, user.getUsername(), user.getScore().get("score")));
    }

    public Dart removeDart(String gameId, String userId, Dart dart) {
        return getGameData(gameId).getGameUser(userId).removeX01(dart);
    }

    public void notifyWebClient(String gameId, String webId)
    {
        Game game = getGameData(gameId);

        template.convertAndSend("/topic/notification/" + webId, new GameMetaNotification(
                game.getUsers(),
                "XO1"));

        this.webId = webId;
    }


}
