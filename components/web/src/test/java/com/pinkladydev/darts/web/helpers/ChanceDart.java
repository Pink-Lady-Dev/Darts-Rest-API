package com.pinkladydev.darts.web.helpers;

import com.pinkladydev.darts.game.Dart;
import com.pinkladydev.darts.game.DartResponseType;
import com.pinkladydev.darts.game.GameType;

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
                getRandomBoolean(),
                DartResponseType.SUCCESS
        );
    }

    public static Dart getRandomAcceptableDart(GameType gameType){
        return new Dart(
                UUID.randomUUID().toString(),
                getRandomNumberBetween(0,2),
                gameType == GameType.CRICKET
                        ? cricketNumber() : getRandomNumberBetween(1, 20),
                getRandomBoolean(),
                getRandomBoolean(),
                DartResponseType.SUCCESS);
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

    private static Integer cricketNumber(){
        return List.of(15,16,17,18,19,20,25).get(getRandomNumberBetween(0, 6));
    }
}
