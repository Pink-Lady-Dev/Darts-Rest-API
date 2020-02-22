package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;


public class Game {

    enum GAME_TYPE {
        X01
    }

    private String  id;
    Hashtable<String, GameUser> gameUsers;

    public Game(@JsonProperty String gameId, @JsonProperty User[] users, @JsonProperty String game_type) {
        this.id = gameId;

        GAME_TYPE game_type_ENUM = GAME_TYPE.X01;
        if (game_type.equals("X01")){
            game_type_ENUM = GAME_TYPE.X01;
        }

        gameUsers = new Hashtable<String, GameUser>();
        for (User user : users){
            GameUser tempGU = new GameUser(user, game_type_ENUM);


            // This may seem weird to do it this way now but this important
            //      as it will allow us to have different methods of scoring
            //      (thinking of cricket specifically)
            // TODO : add game specific rules
            tempGU.addScoreKey("points", 301);

            this.gameUsers.put(user.getId(),tempGU);

        }
    }

    public String getId() {
        return id;
    }

    public Hashtable<String, GameUser> getGameUsers(){
        return gameUsers;
    }

    @JsonIgnore
    // @GetMapping uses all serializes all getters, so this suppresses this from being returned
    public List<User> getUsers() {
        return gameUsers
                .entrySet()
                .stream()
                .map(
                        dict -> (dict.getValue().getUser())
                ).collect(Collectors.toList());
    }

    public GameUser getGameUser(String userId) {
        GameUser temp = gameUsers.get(userId);
        return temp;
    }

    public List<Dart> getUserDarts(String userId){
        return getGameUser(userId).getDarts();
    }



}
