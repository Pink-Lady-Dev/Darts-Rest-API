package com.pinkladydev.darts.web;

import com.pinkladydev.darts.game.Game;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GameService;
import com.pinkladydev.darts.web.models.GameNotificationRequest;
import com.pinkladydev.darts.web.models.GameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RequestMapping("")
@RestController
@CrossOrigin(origins = "*")
@EnableWebMvc
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    /**   PATH: /game   **/
    /**   POST new game **/

    @PostMapping("/game")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGame(@RequestBody GameRequest gameRequest) {
        gameService.createGame(gameRequest.getId(), gameRequest.getUsers(), gameRequest.getGameType());
    }

    /**   PATH: /game/{gameid}   **/
    /**   GET game data for id  **/

    @GetMapping("/game/{gameId}")
    public Game getGameData(@PathVariable String gameId) {
        return gameService.getGameData(gameId);
    }

    @PostMapping("/game/{gameId}")
    public void postGameDataToWeb(@PathVariable String gameId, @RequestBody GameNotificationRequest gameNotificationRequest) {
         gameService.notifyWebClient(gameId, gameNotificationRequest.getWebId());
    }

    /**   PATH: /game/{gameid}/user   **/
    /**   GET users in game  **/

    @GetMapping("/game/{gameId}/user")
    public List<GamePlayer> getGameUsers(@PathVariable String gameId) {
        return gameService.getUsersInGame(gameId);
    }


    /**   PATH: /game/{gameid}/user/{userid}   **/
    /**   GET user game data  **/
    /**   POST new user dart to game  **/
    /**   DELETE user dart from game  **/

    @GetMapping("/game/{gameId}/user/{userId}")
    public GamePlayer getUserGameData(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId) {
        return gameService.getGameData(gameId).getGameUser(userId);
    }

    @PostMapping("/game/{gameId}/user/{userId}")
    public void addUserGameDart(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId, @RequestBody Dart dart){
        gameService.addDart(gameId, userId, dart);
    }

    @DeleteMapping("/game/{gameId}/user/{userId}")
    public Dart removeUserGameDart(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId, @RequestBody Dart dart){
        return gameService.removeDart(gameId, userId, dart);
    }

}
