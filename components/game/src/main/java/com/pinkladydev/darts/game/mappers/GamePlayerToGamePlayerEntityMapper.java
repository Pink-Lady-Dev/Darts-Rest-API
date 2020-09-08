package com.pinkladydev.darts.game.mappers;

import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GamePlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;
import static com.pinkladydev.darts.game.HashGenerator.generateGamePlayerHash;
import static java.util.stream.Collectors.toList;

public class GamePlayerToGamePlayerEntityMapper {

    public static GamePlayerEntity map(GamePlayer gamePlayer) {

        try {
            final List<Map<String, String>> darts = gamePlayer.getDarts()
                    .stream()
                    .map(GamePlayerToGamePlayerEntityMapper::mapDartToDictionary).collect(toList());

            return aGamePlayerEntityBuilder()
                    .id(generateGamePlayerHash(gamePlayer.getGameId(), gamePlayer.getGameId()))
                    .gameId(gamePlayer.getGameId())
                    .username(gamePlayer.getUsername())
                    .gameType(gamePlayer.getGameType())
                    .score(gamePlayer.getScore())
                    .darts(darts)
                    .wins(new ArrayList<>())
                    .losses(new ArrayList<>())
                    .build();
        } catch (Exception ignored){

        }

        return null;
    }

    private static Map<String, String> mapDartToDictionary(Dart d){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", d.getId());
        hashMap.put("throwNumber", d.getThrowNumber().toString());
        hashMap.put("pie", d.getPie().toString());
        hashMap.put("isDouble", d.isDouble().toString());
        hashMap.put("isTriple", d.isTriple().toString());
        return hashMap;
    }
}
