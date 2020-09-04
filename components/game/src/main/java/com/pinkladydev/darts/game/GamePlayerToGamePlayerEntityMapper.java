package com.pinkladydev.darts.game;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;

import static com.pinkladydev.darts.game.GamePlayerEntity.aGamePlayerEntityBuilder;

public class GamePlayerToGamePlayerEntityMapper {

    public static GamePlayerEntity map(GamePlayer gamePlayer) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            final String hash = toHexString(messageDigest.digest(
                    (gamePlayer.getGameId() + gamePlayer.getUsername()).getBytes(StandardCharsets.UTF_8)));

            return aGamePlayerEntityBuilder()
                    .id(hash)
                    .gameId(gamePlayer.getGameId())
                    .username(gamePlayer.getUsername())
                    .gameType(gamePlayer.getGameType())
                    .score(gamePlayer.getScore())
                    .darts(gamePlayer.getDarts())
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
}
