package com.pinkladydev.darts.player;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Document(collection = "Players")
public class PlayerEntity {

    @Id
    private final String  id; // connection to user
    private final String username;

    private final List<String> gameLog;
    private final List<String> leagues;

    public static PlayerEntity newPlayerEntity(String username){
        return aPlayerEntityBuilder()
                .username(username)
                .gameLog(new ArrayList<>())
                .leagues(new ArrayList<>())
                .build();
    }
    public static PlayerEntityBuilder aPlayerEntityBuilder(){
        return builder();
    }
}
