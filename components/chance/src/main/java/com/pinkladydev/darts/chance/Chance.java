package com.pinkladydev.darts.chance;

import java.util.Random;

public class Chance {

    public static String getRandomAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    public static Integer getRandomNumberBetween(Integer lower, Integer upper){
        return new Random().nextInt(upper - lower + 1) + lower;
    }

    public static Boolean getRandomBoolean(){
        return new Random().nextInt() % 2 == 1;
    }
}
