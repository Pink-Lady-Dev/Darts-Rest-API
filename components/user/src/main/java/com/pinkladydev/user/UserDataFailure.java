package com.pinkladydev.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UserDataFailure extends RuntimeException{

    public UserDataFailure(String message) {
        super(message);
    }

    public static UserDataFailure failureToSaveUserToMongo(String message)
    {
        return new UserDataFailure("Failed to save user to mongo repository. | Message: " + message);
    }

    public static UserDataFailure UsernameOrIdNotFoundInMongo(String user, String message)
    {
        return new UserDataFailure("Failed to save user to mongo repository. | User: " + user + " | Message: "+ message);
    }
}
