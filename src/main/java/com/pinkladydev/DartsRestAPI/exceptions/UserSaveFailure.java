package com.pinkladydev.DartsRestAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UserSaveFailure extends RuntimeException{

    public UserSaveFailure(String message) {
        super(message);
    }

    public static UserSaveFailure failureToSaveUserToMongo(String message)
    {
        return new UserSaveFailure("Failed to save user to mongo repository. | Message: " + message);
    }
}
