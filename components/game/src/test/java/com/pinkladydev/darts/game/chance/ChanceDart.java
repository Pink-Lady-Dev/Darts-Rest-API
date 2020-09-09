package com.pinkladydev.darts.game.chance;

import com.pinkladydev.darts.game.Dart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;

public class ChanceDart {

    public static Dart getRandomDart(){
        return new Dart(
                UUID.randomUUID().toString(),
                getRandomNumberBetween(0,2),
                getRandomNumberBetween(1,20),
                getRandomBoolean(),
                getRandomBoolean());
    }

    public static Dart getRandomDart(List<Integer> acceptablePie){
        return new Dart(
                UUID.randomUUID().toString(),
                getRandomNumberBetween(0,2),
                acceptablePie.get(getRandomNumberBetween(0, acceptablePie.size() - 1)),
                getRandomBoolean(),
                getRandomBoolean());
    }

    public static Map<String, String> getRandomDartMap(){
        final Boolean isTriple = getRandomBoolean();
        //{ "throwNumber" : "0", "pie" : "12", "did" : "08eadb76-98ed-46ae-a85e-c5fdea097536", "isTriple" : "true", "points" : "36", "isDouble" : "false" }
        final Map<String,String> dart = new HashMap<>();
        dart.put("throwNumber",getRandomNumberBetween(0,2).toString());
        dart.put("pie",getRandomNumberBetween(1,20).toString());
        dart.put("id", UUID.randomUUID().toString());
        dart.put("isTriple", isTriple.toString());
        dart.put("isDouble", isTriple ? "false" : getRandomBoolean().toString());

        return dart;
    }
}
