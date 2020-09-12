package com.pinkladydev.darts.game;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    public static String generateGamePlayerHash(String gameId, String userId){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            return toHexString(messageDigest.digest(
                    (gameId + userId).getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException noSuchAlgorithmException){
            throw new RuntimeException("Message Digest failed to find instance.");
        }
    }


    private static String toHexString(byte[] bytes) {
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
