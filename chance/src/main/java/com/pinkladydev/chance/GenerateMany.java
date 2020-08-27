package com.pinkladydev.chance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GenerateMany {

    public static <G> List<G> generateListOf(Generator<G> my, Integer size){
        List<G> ll = new ArrayList<>();
        IntStream.rangeClosed(0, size - 1).forEach((i) -> ll.add( my.generate()));
        return ll;
    }
}
