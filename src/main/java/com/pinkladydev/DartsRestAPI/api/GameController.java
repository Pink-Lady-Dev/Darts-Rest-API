package com.pinkladydev.DartsRestAPI.api;

import com.pinkladydev.DartsRestAPI.model.Dart;
import com.pinkladydev.DartsRestAPI.model.Game;
import com.pinkladydev.DartsRestAPI.model.GameUser;
import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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


    /**   PATH: /game/{gameid}   **/
    /**   GET game data for id **/
    /**   POST new game with id  **/

    @GetMapping("/game/{gameId}")
    public Game getGameData(@PathVariable String gameId) {
        Game temp = gameService.getGameData(gameId);
        return temp;
    }

    @PostMapping("/game/{gameId}")
    public void createGame(@RequestBody Game game) {
        gameService.createGame(game);
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
    public GameUser getUserGameData(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId) {
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

//    @DeleteMapping("/game/{gameId}/user/{userId}")
//    public Dart removeUserGameDart(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId){
//        return gameService.getGameData(gameId).getGameUser(userId).removeDart();
//    }
}
