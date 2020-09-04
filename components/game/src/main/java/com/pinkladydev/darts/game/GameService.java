package com.pinkladydev.darts.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class GameService {

    private final GameDao gameDao;

    private String webId;

    private SimpMessagingTemplate template;

    @Autowired
    public GameService(@Qualifier("FakeGameDao") GameDao gameDao, SimpMessagingTemplate template){
        this.gameDao = gameDao;
        this.template = template;
        this.webId = null;
    }

    public Game getGameData (String gameID) {
        return gameDao.getTotalGame(gameID);
    }

    public void createGame(String id, List<String> usernames, String gameType) {

        // Maybe save this till game ends ??
        // Do we want to save state during game or keep a list in live mem

        // Validate player username is real
        List<GamePlayer> gamePlayers;
        final String gameId = UUID.randomUUID().toString();

        if (gameType.equals("X01")){
            gamePlayers = usernames.stream().map(player -> GamePlayer.StartX01(gameId, player, 301)).collect(toList());
        } else {
            //throw something about improper game type
            throw new RuntimeException("e");
        }

        try{
            gamePlayers.forEach(gameDao::createGame);
        } catch (NullPointerException nullPointerException){
            // Failed to create game
        }
    }

    public List<GamePlayer> getUsersInGame(String gameId) {
        return getGameData(gameId).getGamePlayers();
    }

    public void addDart(String gameId, String userId, Dart dart) {
        GamePlayer u = getGameData(gameId).getGameUser(userId);
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
                getGameData(gameId).getGamePlayers(),
                "X01"));

    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }
}
