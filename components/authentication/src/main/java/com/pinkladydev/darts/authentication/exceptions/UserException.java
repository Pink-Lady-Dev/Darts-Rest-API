package com.pinkladydev.darts.authentication.exceptions;

public class UserException extends RuntimeException{

    private UserException(String message) {
        super(message);
    }

    public static UserException InvalidUserCredentials(String username){
        return new UserException("Invalid user credentials. | Username: " + username);
    }

    public static UserException invalidKeyWhenCreatingUser()
    {
        return new UserException("Invalid key when creating user");
    }

    public static UserException invalidKeyWhenAuthenticatingUser()
    {
        return new UserException("Invalid key when authenticating user:");
    }
}
