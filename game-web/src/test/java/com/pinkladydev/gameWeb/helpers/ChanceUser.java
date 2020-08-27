package com.pinkladydev.gameWeb.helpers;

import com.pinkladydev.gameWeb.api.models.UserRequest;
import com.pinkladydev.gameWeb.model.User;

import static com.pinkladydev.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.gameWeb.model.User.aUserBuilder;

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
