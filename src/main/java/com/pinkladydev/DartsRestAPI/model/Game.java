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
    private final HashMap<String, User> gameUsers;


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

        for (User gUser : users)
        {
            // TODO this can definitely be done functionally
            gameUsers.put(gUser.getId(), gUser);
        }
    }

    public String getId() {
        return id;
    }

    public Map<String, User> getGameUsers(){
        return gameUsers;
    }

    @JsonIgnore
    // @GetMapping uses all serializes all getters, so this suppresses this from being returned
    public List<User> getUsers() {
        return gameUsers
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public User getGameUser(String userId) {
        return gameUsers.get(userId);
    }

    public List<Dart> getUserDarts(String userId){
        return getGameUser(userId).getDarts();
    }

}
