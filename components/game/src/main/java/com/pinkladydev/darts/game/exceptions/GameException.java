package com.pinkladydev.darts.game.exceptions;

public class GameException extends RuntimeException {

    private GameException(String message){
        super(message);
    }

    static public GameException GamePlayerNotFound(String username){
        return new GameException("Game Player could not be found with entered username. | Username: " + username);
    }

    static public GameException InvalidGameType(String gameType){
        return new GameException("Game Type entered is not valid. | Valid Types: {X01, CRICKET} | Game Type: " + gameType);
    }
}
