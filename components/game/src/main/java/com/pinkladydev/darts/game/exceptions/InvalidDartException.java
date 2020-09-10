package com.pinkladydev.darts.game.exceptions;

public class InvalidDartException extends RuntimeException{

    private InvalidDartException(String message){
        super(message);
    }

    static public InvalidDartException InvalidCricketDartException(String pie){
        return new InvalidDartException("Invalid Dart in Cricket | Pie(" + pie + ") is not allowed in game of Cricket.");
    }

    static public InvalidDartException InvalidDoubleAndTripleDartException(){
        return new InvalidDartException("Invalid Dart in Cricket | Dart could not be created because a dart cannot be both double and triple");
    }
}
