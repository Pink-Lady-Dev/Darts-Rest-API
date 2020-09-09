package com.pinkladydev.darts.game.exceptions;

public class GamePlayerException extends RuntimeException{

    private GamePlayerException(String message){
        super(message);
    }

    static public GamePlayerException InvalidCricketDartException(String pie){
        return new GamePlayerException("Invalid Dart in Cricket | Pie: " + pie + ".");
    }
}
