package com.pinkladydev.DartsRestAPI.api;

import com.pinkladydev.DartsRestAPI.api.models.GameNotificationRequest;
import com.pinkladydev.DartsRestAPI.api.models.GameRequest;
import com.pinkladydev.DartsRestAPI.model.Dart;
import com.pinkladydev.DartsRestAPI.model.Game;
import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("")
@RestController
@CrossOrigin(origins = "*")
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
        gameService.createGame(gameRequest);
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
    public List<User> getGameUsers(@PathVariable String gameId) {
        return gameService.getUsersInGame(gameId);
    }


    /**   PATH: /game/{gameid}/user/{userid}   **/
    /**   GET user game data  **/
    /**   POST new user dart to game  **/
    /**   DELETE user dart from game  **/

    @GetMapping("/game/{gameId}/user/{userId}")
    public User getUserGameData(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId) {
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
