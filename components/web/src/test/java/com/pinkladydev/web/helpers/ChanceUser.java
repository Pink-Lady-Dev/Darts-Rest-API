package com.pinkladydev.web.helpers;

import com.pinkladydev.web.api.models.UserRequest;
import com.pinkladydev.user.User;

import static com.pinkladydev.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.user.User.aUserBuilder;

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