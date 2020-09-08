package com.pinkladydev.darts.game.mappers;

import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.GamePlayer;
import com.pinkladydev.darts.game.GamePlayerEntity;

import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.game.GamePlayer.aGamePlayerBuilder;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class GamePlayerEntityToGamePlayerMapper {

    public static GamePlayer map(GamePlayerEntity gamePlayerEntity){

        final List<Dart> dartList = gamePlayerEntity.getDarts().stream().map(GamePlayerEntityToGamePlayerMapper::mapDartDictionaryToDart).collect(toList());
        return aGamePlayerBuilder()
                .username(gamePlayerEntity.getUsername())
                .gameId(gamePlayerEntity.getGameId())
                .gameType(gamePlayerEntity.getGameType())
                .darts(dartList)
                .score(gamePlayerEntity.getScore())
                .build();
    }

    private static Dart mapDartDictionaryToDart(Map<String, String> d){
        return new Dart(d.get("id"), parseInt(d.get("throwNumber")), parseInt(d.get("pie")), parseBoolean(d.get("isDouble")), parseBoolean(d.get("isTriple")));
    }
}
