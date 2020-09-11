package com.pinkladydev.darts.game;

import com.pinkladydev.darts.game.exceptions.InvalidDartException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomBoolean;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.chance.GenerateMany.generateListOf;
import static com.pinkladydev.darts.game.chance.ChanceDart.getRandomDart;
import static com.pinkladydev.darts.game.chance.ChanceGamePlayer.getCricketRandomGamePlayerWithDarts;
import static com.pinkladydev.darts.game.chance.ChanceGamePlayer.getRandomGamePlayerWithDarts;
import static com.pinkladydev.darts.game.chance.ChanceGamePlayer.getX01RandomGamePlayerWithDarts;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamePlayerTest {

    /** Start Games **/
    @Test
    void startX01_shouldCreateNewGamePlayerInX01Game() {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final int startingScore = getRandomNumberBetween(3,10) * 100 + 1;
        final Map<String, Integer> initScore = new HashMap<>();
        initScore.put("score", startingScore);

        final GamePlayer x01GamePlayer = GamePlayer.StartX01(gameId, username, startingScore);

        assertEquals(x01GamePlayer.getGameId(), gameId);
        assertEquals(x01GamePlayer.getUsername(), username);
        assertEquals(x01GamePlayer.getGameType(), GameType.X01);
        assertEquals(x01GamePlayer.getScore(), initScore);
        assertEquals(x01GamePlayer.getDarts(), new ArrayList<Dart>());
    }

    @Test
    void startCricket_shouldCreateNewGamePlayerInCricketGame() {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final String username = getRandomAlphaNumericString(getRandomNumberBetween(5,20));
        final Map<String, Integer> initScore = new HashMap<>();
        initScore.put("20", 0);
        initScore.put("19", 0);
        initScore.put("18", 0);
        initScore.put("17", 0);
        initScore.put("16", 0);
        initScore.put("15", 0);
        initScore.put("25", 0);

        final GamePlayer cricketGamePlayer = GamePlayer.StartCricket(gameId, username);

        assertEquals(cricketGamePlayer.getGameId(), gameId);
        assertEquals(cricketGamePlayer.getUsername(), username);
        assertEquals(cricketGamePlayer.getGameType(), GameType.CRICKET);
        assertEquals(cricketGamePlayer.getScore(), initScore);
        assertEquals(cricketGamePlayer.getDarts(), new ArrayList<Dart>());
    }

    /** X01 Game Scenarios **/
    @Test
    void addDart_toXO1Game_onThrowZeroOrOne_shouldAddDartToDartList_AndSubtractItFromScore_AndReturnDartWithTypeNextThrow() {
        final GamePlayer gamePlayer = getX01RandomGamePlayerWithDarts();
        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart(getRandomBoolean() ? 0 : 1);
        expectedDartIdList.add(dart.getId());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();
        expectedScore.put("score", expectedScore.get("score") - dart.getPoints());

        final Dart actualDart = gamePlayer.addDart(new Dart(dart));

        assertEquals(gamePlayer.getScore(), expectedScore);
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
        assertEquals(actualDart.getDartResponseType(), DartResponseType.NEXT_THROW);
        assertThat(actualDart).isEqualToIgnoringGivenFields(dart, "dartResponseType");
    }

    @Test
    void addDart_toXO1Game_onThrowTwo_shouldAddDartToDartList_AndSubtractItFromScore_AndReturnDartWithTypeRoundOver() {
        final GamePlayer gamePlayer = getX01RandomGamePlayerWithDarts();
        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart(2);
        expectedDartIdList.add(dart.getId());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();
        expectedScore.put("score", expectedScore.get("score") - dart.getPoints());

        final Dart actualDart = gamePlayer.addDart(new Dart(dart));

        assertEquals(gamePlayer.getScore(), expectedScore);
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
        assertEquals(actualDart.getDartResponseType(), DartResponseType.ROUND_OVER);
        assertThat(actualDart).isEqualToIgnoringGivenFields(dart, "dartResponseType");
    }

    @Test
    void addDart_toXO1Game_andPlayerScoreEqualsZero_shouldAddDartToDartList_AndSetScoreToZero_AndReturnDartWithTypeGameOver() {
        final GamePlayer gamePlayer = getX01RandomGamePlayerWithDarts();
        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart();
        expectedDartIdList.add(dart.getId());

        gamePlayer.getScore().put("score", dart.getPoints());

        final Dart actualDart = gamePlayer.addDart(new Dart(dart));

        assertEquals(gamePlayer.getScore(), Map.of("score",0));
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
        assertEquals(actualDart.getDartResponseType(), DartResponseType.GAME_OVER);
        assertThat(actualDart).isEqualToIgnoringGivenFields(dart, "dartResponseType");
    }

    @Test
    void addDart_toXO1Game_andPlayerScoreGoesPastZero_shouldAddDartToDartList_AndNotChangeScore_AndReturnDartWithTypeBust() {
        final GamePlayer gamePlayer = getX01RandomGamePlayerWithDarts();
        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart();
        expectedDartIdList.add(dart.getId());

        gamePlayer.getScore().put("score", 0);

        final Dart actualDart = gamePlayer.addDart(new Dart(dart));

        assertEquals(gamePlayer.getScore(), Map.of("score",0 ));
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
        assertEquals(actualDart.getDartResponseType(), DartResponseType.BUST);
        assertThat(actualDart).isEqualToIgnoringGivenFields(dart, "dartResponseType");
    }

    @Test
    void removeDart_fromXO1Game_shouldAddDartBackToScoreAndRemoveFromDartList() {
        final GamePlayer gamePlayer = getX01RandomGamePlayerWithDarts();

        final List<Dart> expectedDarts = new ArrayList<>(gamePlayer.getDarts());
        final Dart removedDart = expectedDarts.remove(expectedDarts.size() - 1);
        final List<String> expectedDartIdList = expectedDarts.stream().map(Dart::getId).collect(toList());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();
        expectedScore.put("score", expectedScore.get("score") + removedDart.getPoints());

        gamePlayer.removeDart();

        assertEquals(gamePlayer.getScore(), expectedScore);
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
    }

    /** Cricket Game Scenarios **/
    @Test
    void addDart_toCricketGame_onThrowZeroOrOne_shouldAddDartToDartList_AndAddToCorrectCategory_AndReturnDartWithTypeNextThrow(){
        final GamePlayer gamePlayer = getCricketRandomGamePlayerWithDarts();
        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart(getRandomBoolean() ? 0 : 1, List.of(15,16,17,18,19,20,25));
        expectedDartIdList.add(dart.getId());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();
        expectedScore.put(dart.getPie().toString(), expectedScore.get(dart.getPie().toString()) + 1);

        final Dart actualDart = gamePlayer.addDart(new Dart(dart));

        assertEquals(gamePlayer.getScore(), expectedScore);
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
        assertEquals(actualDart.getDartResponseType(), DartResponseType.NEXT_THROW);
        assertThat(actualDart).isEqualToIgnoringGivenFields(dart, "dartResponseType");
    }

    @Test
    void addDart_toCricketGame_onThrowTwo_shouldAddDartToDartList_AndAddToCorrectCategory_AndReturnDartWithTypeRoundOver(){
        final GamePlayer gamePlayer = getCricketRandomGamePlayerWithDarts();
        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart(2, List.of(15,16,17,18,19,20,25));
        expectedDartIdList.add(dart.getId());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();
        expectedScore.put(dart.getPie().toString(), expectedScore.get(dart.getPie().toString()) + 1);

        final Dart actualDart = gamePlayer.addDart(new Dart(dart));

        assertEquals(gamePlayer.getScore(), expectedScore);
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
        assertEquals(actualDart.getDartResponseType(), DartResponseType.ROUND_OVER);
        assertThat(actualDart).isEqualToIgnoringGivenFields(dart, "dartResponseType");
    }

    @Test
    void addDart_toCricketGame_shouldAddDartToDartList_AndAddToCorrectCategory_AndReturnDartWithTypeGameOver(){
        final GamePlayer gamePlayer = getCricketRandomGamePlayerWithDarts();
        gamePlayer.getScore().keySet().forEach(key -> gamePlayer.getScore().put(key, 3));

        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart(getRandomNumberBetween(0,2), List.of(15,16,17,18,19,20,25));
        expectedDartIdList.add(dart.getId());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();

        expectedScore.put(dart.getPie().toString(), expectedScore.get(dart.getPie().toString()) + 1);

        final Dart actualDart = gamePlayer.addDart(new Dart(dart));

        assertEquals(gamePlayer.getScore(), expectedScore);
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);
        assertEquals(actualDart.getDartResponseType(), DartResponseType.GAME_OVER);
        assertThat(actualDart).isEqualToIgnoringGivenFields(dart, "dartResponseType");
    }

    @Test
    void addDart_invalidDart_toCricketGame_shouldNotChangeScoreAndThrowException() {
        final GamePlayer gamePlayer = getCricketRandomGamePlayerWithDarts();
        final List<String> expectedDartIdList = gamePlayer.getDarts().stream().map(Dart::getId).collect(toList());
        final Dart dart = getRandomDart(getRandomBoolean() ? 0 : 1, List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14));
        expectedDartIdList.add(dart.getId());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();
        expectedScore.put(dart.getPie().toString(), expectedScore.get(dart.getPie().toString()));

        assertThrows(InvalidDartException.class, () -> gamePlayer.addDart(dart), "");

        assertEquals(gamePlayer.getScore(), expectedScore);
        gamePlayer.getDarts().forEach(d -> assertTrue(expectedDartIdList.contains(d.getId())));
    }

    @Test
    void removeDart_toCricketGame_shouldAddSubtractOneFromScoreAndRemoveFromDartList() {
        final GamePlayer gamePlayer = getCricketRandomGamePlayerWithDarts();

        final List<Dart> expectedDarts = new ArrayList<>(gamePlayer.getDarts());
        final Dart removedDart = expectedDarts.remove(expectedDarts.size() - 1);
        final List<String> expectedDartIdList = expectedDarts.stream().map(Dart::getId).collect(toList());

        final Map<String, Integer> expectedScore = gamePlayer.getScore();
        expectedScore.put(removedDart.getPie().toString(), expectedScore.get(removedDart.getPie().toString()) - 1);

        gamePlayer.removeDart();

        assertEquals(gamePlayer.getScore(), expectedScore);
        assertEquals(gamePlayer.getDarts().stream().map(Dart::getId).collect(toList()), expectedDartIdList);

    }

    /** Wins and Losses **/
    @Test
    void winGame_shouldAddRemainingOpponentsToWinsList(){
        final GamePlayer gamePlayer = getRandomGamePlayerWithDarts();
        gamePlayer.getWins().addAll(
                generateListOf(
                    () -> getRandomAlphaNumericString(getRandomNumberBetween(5, 20)),
                    getRandomNumberBetween(0,2)));

        final List<String> opponents = generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5, 20)),
                getRandomNumberBetween(1,3));

        gamePlayer.winGame(opponents);

        opponents.forEach(opponent -> assertTrue(gamePlayer.getWins().contains(opponent)));
    }

    @Test
    void loseGame_shouldAddOpponentsToLossesList(){
        final GamePlayer gamePlayer = getRandomGamePlayerWithDarts();
        gamePlayer.getLosses().addAll(
                generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5, 20)),
                        getRandomNumberBetween(0,2)));

        final String opponent = getRandomAlphaNumericString(getRandomNumberBetween(5, 20));

        gamePlayer.loseGame(opponent);

        assertTrue( gamePlayer.getLosses().contains(opponent));
    }
}
