package com.pinkladydev.darts.player;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Player {

    private final String  id; // connection to user
    private final String username;

    private final List<String> gameLog;
    private final List<String> leagues;

    public static PlayerBuilder aPlayerBuilder(){
        return builder();
    }
}
