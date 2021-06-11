package com.pinkladydev.darts.game;

import com.pinkladydev.darts.game.notifications.DartNotification;
import com.pinkladydev.darts.game.notifications.GameMetaNotification;
import com.pinkladydev.darts.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.pinkladydev.darts.game.HashGenerator.generateGamePlayerHash;
import static com.pinkladydev.darts.game.exceptions.GameException.GamePlayerNotFound;
import static com.pinkladydev.darts.game.exceptions.GameException.InvalidGameType;
import static java.util.stream.Collectors.toList;

@Service
public class GameService {

    private final GameDao gameDao;

    private final PlayerService playerService;

    private String webId;

    private SimpMessagingTemplate template;

    @Autowired
    public GameService(@Qualifier("MongoGame") final GameDao gameDao,
                       final PlayerService playerService,
                       final SimpMessagingTemplate template){
        this.gameDao = gameDao;
        this.playerService = playerService;
        this.template = template;
        this.webId = null;
    }

    public Game getGameData (String gameID) {
        return gameDao.getTotalGame(gameID);
    }

    public String createGame(List<String> usernames, String gameType) {

        final List<GamePlayer> gamePlayers;
        usernames.forEach((username) -> {
            if (!playerService.doesPlayerExist(username)) {
                throw GamePlayerNotFound(username);}});

        final String gameId = UUID.randomUUID().toString();

        if (gameType.equals("X01")){
            gamePlayers = usernames.stream().map(player -> GamePlayer.StartX01(gameId, player, 301)).collect(toList());
        } else if (gameType.equals("CRICKET")){
            gamePlayers = usernames.stream().map(player -> GamePlayer.StartCricket(gameId, player)).collect(toList());
        } else {
            throw InvalidGameType(gameType);
        }

        gamePlayers.forEach(gamePlayer -> {
          gameDao.save(gamePlayer);
          playerService.addGameLog(gamePlayer.getUsername(), generateGamePlayerHash(gamePlayer.getUsername(), gamePlayer.getGameId()));
        });
        return gameId;
    }

    public List<GamePlayer> getGamePlayers(String gameId) {
        return gameDao.getGamePlayers(gameId);
    }

    public Dart addDart(String gameId, String userId, int throwNumber, int pie, boolean isDouble, boolean isTriple) {

        Dart dart = new Dart(throwNumber, pie, isDouble, isTriple);

        final GamePlayer gamePlayer = gameDao.getGamePlayer(gameId,userId);
        dart = gamePlayer.addDart(dart);

        if (dart.getDartResponseType() == DartResponseType.GAME_OVER){
            final List<GamePlayer> losers = gameDao.getGamePlayers(gameId).stream()
                    .filter(player -> !gamePlayer.getUsername().equals(player.getUsername()))
                    .collect(toList());

            gamePlayer.winGame(losers.stream().map(GamePlayer::getUsername).collect(toList()));
            gameDao.save(gamePlayer);

            losers.forEach(player -> {
                player.loseGame(gamePlayer.getUsername());
                gameDao.save(player);
            });
        } else{
            gameDao.save(gamePlayer);

            // We should check if this is actually listening ( aka if webId is empty or not)
            template.convertAndSend(
                    "/topic/notification/" + this.webId,
                    new DartNotification(dart, gamePlayer.getUsername(), gamePlayer.getScore().get("score")));
        }


        return dart;
    }

    public void removeLastDart(String gameId, String userId) {
        GamePlayer gamePlayer = gameDao.getGamePlayer(gameId,userId);
        gamePlayer.removeDart();

        gameDao.save(gamePlayer);

        // We should check if this is actually listening ( aka if webId is empty or not)
        template.convertAndSend(
                "/topic/notification/" + this.webId,
                new RemovedDartNotification(gamePlayer.getUsername(), gamePlayer.getScore().get("score")));
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
