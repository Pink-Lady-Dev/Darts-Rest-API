package com.pinkladydev.DartsRestAPI.helpers;

import static com.pinkladydev.DartsRestAPI.helpers.Chance.*;

public class ChanceUser {

    public static com.pinkladydev.DartsRestAPI.model.User randomUser(){
        return com.pinkladydev.DartsRestAPI.model.User.aUserBuilder()
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .password(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .build();
    }
}
