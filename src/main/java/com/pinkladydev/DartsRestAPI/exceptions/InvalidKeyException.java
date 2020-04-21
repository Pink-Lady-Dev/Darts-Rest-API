package com.pinkladydev.DartsRestAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidKeyException extends DartsException {

    private InvalidKeyException(String message) {
        super(message);
    }

    public static InvalidKeyException invalidKeyWhenCreatingUser()
    {
        System.out.println("Invalid key when creating user");
        return new InvalidKeyException("Invalid key when creating user");
    }

    public static InvalidKeyException invalidKeyWhenAuthenticatingUser()
    {
        return new InvalidKeyException("Invalid key when authenticating user:");
    }
}
