package com.pinkladydev.game;

import com.pinkladydev.user.Dart;
import com.pinkladydev.user.User;
import com.pinkladydev.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameDao gameDao;
    private final UserDao userDao;

    private String webId;

    private SimpMessagingTemplate template;

    @Autowired
    public GameService(@Qualifier("FakeGameDao") GameDao gameDao,@Qualifier("Mongo") UserDao userDao, SimpMessagingTemplate template){
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.template = template;
        this.webId = null;
    }

    public Game getGameData (String gameID) {
        return gameDao.getGameData(gameID);
    }

    public void createGame(String id, List<String> usernames, String gameType) {
        List<User> users =usernames.stream().map(userDao::getUser).collect(Collectors.toList());
        users.forEach(user -> user.StartX01(301));

        gameDao.createGame(new Game(id, users, gameType));

    }

    public List<User> getUsersInGame(String gameId) {
        return getGameData(gameId).getUsers();
    }

    public void addDart(String gameId, String userId, Dart dart) {
        User u = getGameData(gameId).getGameUser(userId);
        u.addX01(dart);

        // WE should check if this is actually listening ( aka if webId is empty or not)
        template.convertAndSend("/topic/notification/" + this.webId, new DartNotification(dart, u.getUsername(), u.getScore().get("score")));
    }

    public Dart removeDart(String gameId, String userId, Dart dart) {
        return getGameData(gameId).getGameUser(userId).removeX01(dart);
    }

    public void notifyWebClient(String gameId, String webId)
    {
        this.webId = webId;

        template.convertAndSend("/topic/notification/" + webId, new GameMetaNotification(
                getGameData(gameId).getUsers(),
                "X01"));

    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }
}