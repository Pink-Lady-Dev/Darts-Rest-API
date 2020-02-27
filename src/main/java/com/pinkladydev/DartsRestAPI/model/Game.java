package com.pinkladydev.DartsRestAPI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Game {

    enum GAME_TYPE {
        X01
    }

    private final String  id;
    private final HashMap<String, GameUser> gameUsers;


    public Game(
            @JsonProperty("id") String id,
            @JsonProperty("users") User[] users,
            @JsonProperty("gameType") String gameType)
    {
        this.id = id;
        this.gameUsers = new HashMap<>();

        GAME_TYPE gameTypeEnum = GAME_TYPE.X01;
        if (gameType.equals("X01")){
            gameTypeEnum = GAME_TYPE.X01;
        }

        for (User user : users){
            GameUser tempGU = new GameUser(user, gameTypeEnum);

            switch (gameTypeEnum){
                case X01:
                    tempGU.addScoreKey("points", 301);
                    break;
            }

            this.gameUsers.put(user.getId(),tempGU);
        }

    }

    public String getId() {
        return id;
    }

    public Map<String, GameUser> getGameUsers(){
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
        return gameUsers.get(userId);
    }

    public List<Dart> getUserDarts(String userId){
        return getGameUser(userId).getDarts();
    }



}
