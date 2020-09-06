package com.pinkladydev.darts.game;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;
import static java.util.stream.Collectors.toList;

public class GamePlayerToGamePlayerEntityMapper {

    public static GamePlayerEntity map(GamePlayer gamePlayer) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            final String hash = toHexString(messageDigest.digest(
                    (gamePlayer.getGameId() + gamePlayer.getUsername()).getBytes(StandardCharsets.UTF_8)));

            final List<Map<String, String>> darts = gamePlayer.getDarts()
                    .stream()
                    .map(GamePlayerToGamePlayerEntityMapper::mapDartToDictionary).collect(toList());
            return aGamePlayerEntityBuilder()
                    .id(hash)
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

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
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
