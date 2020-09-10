package com.pinkladydev.darts.game;

import com.pinkladydev.darts.game.exceptions.InvalidDartException;
import org.junit.jupiter.api.Test;

import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DartTest {

    @Test
    void Dart_shouldThrowException_whenBothDoubleAndTripleAreTrue(){
        assertThrows(InvalidDartException.class,
                () -> new Dart(getRandomNumberBetween(0,2), getRandomNumberBetween(1,20), true, true), "");
    }

    @Test
    void getPoints_shouldNotModifyPieValue_whenDoubleAndTripleAreFalse(){
        final int pie = getRandomNumberBetween(1,20);
        final Dart dart = new Dart(getRandomNumberBetween(0,2), pie, false, false);

        assertEquals(dart.getPoints(), pie);
    }

    @Test
    void getPoints_shouldDoublePieValueOfDart_whenDoubleIsTrue(){
        final int pie = getRandomNumberBetween(1,20);
        final Dart dart = new Dart(getRandomNumberBetween(0,2), pie, true, false);

        assertEquals(dart.getPoints(), pie * 2);
    }

    @Test
    void getPoints_shouldDoublePieValueOfDart_whenTripleIsTrue(){
        final int pie = getRandomNumberBetween(1,20);
        final Dart dart = new Dart(getRandomNumberBetween(0,2), pie, false, true);

        assertEquals(dart.getPoints(), pie * 3);
    }
}
