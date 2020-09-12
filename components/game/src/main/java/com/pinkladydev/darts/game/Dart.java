package com.pinkladydev.darts.game;

import java.util.UUID;

import static com.pinkladydev.darts.game.exceptions.InvalidDartException.InvalidDoubleAndTripleDartException;

public class Dart {

    // Throw information
    private final String  id; // Identifier for throw
    private final Integer throwNumber;
    private final Integer pie; // Actual number hit
    private final boolean isDouble;
    private final boolean isTriple;
    private DartResponseType dartResponseType;

    public Dart(Dart dart) {
        this.id = dart.id;
        this.throwNumber = dart.throwNumber;
        this.pie = dart.pie;
        this.isDouble = dart.isDouble;
        this.isTriple = dart.isTriple;
        this.dartResponseType = dart.dartResponseType;
    }

    public Dart(
            Integer throwNumber,
            Integer pie,
            boolean isDouble,
            boolean isTriple) {
        this( UUID.randomUUID().toString(), throwNumber, pie, isDouble, isTriple);
    }

    public Dart(
            String id,
            Integer  throwNumber,
            Integer pie,
            boolean isDouble,
            boolean isTriple) {

        this.id = id;
        this.throwNumber = throwNumber;
        this.pie = pie;

        if (isDouble && isTriple) {
            throw InvalidDoubleAndTripleDartException();
        }

        this.isDouble = isDouble;
        this.isTriple = isTriple;
    }

    public Integer getPoints() {
        return this.pie * (this.isDouble ? 2 : 1) * (this.isTriple ? 3 : 1);
    }

    public Integer getThrowNumber() {
        return throwNumber;
    }

    public Boolean isDouble() {
        return isDouble;
    }

    public Boolean isTriple() {
        return isTriple;
    }

    public Integer getPie() {
        return pie;
    }

    public String getId() {
        return id;
    }

    public DartResponseType getDartResponseType() {
        return dartResponseType;
    }

    public void setDartResponseType(DartResponseType dartResponseType) {
        this.dartResponseType = dartResponseType;
    }
}
