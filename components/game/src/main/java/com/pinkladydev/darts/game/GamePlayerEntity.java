package com.pinkladydev.darts.game;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Document(collection = "Games")
public class GamePlayerEntity{

    @Id
    private final String id;
    private final String username;
    private final String gameId;
    private final GameType gameType;
    private final Map<String, Integer> score;
    private final List<Dart> darts;
    private final List<String> wins;
    private final List<String> losses;


    public static GamePlayerEntityBuilder aGamePlayerEntityBuilder(){
        return builder();
    }
}
