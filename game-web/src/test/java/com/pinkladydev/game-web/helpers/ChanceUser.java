package com.pinkladydev.DartsRestAPI.helpers;

import com.pinkladydev.DartsRestAPI.api.models.UserRequest;
import com.pinkladydev.DartsRestAPI.model.User;

import static com.pinkladydev.DartsRestAPI.helpers.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.DartsRestAPI.helpers.Chance.getRandomNumberBetween;
import static com.pinkladydev.DartsRestAPI.model.User.aUserBuilder;

public class ChanceUser {

    public static User randomUser(){
        return aUserBuilder()
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .password(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .build();
    }

    public static UserRequest randomUserRequest(){
        return new UserRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }
}
