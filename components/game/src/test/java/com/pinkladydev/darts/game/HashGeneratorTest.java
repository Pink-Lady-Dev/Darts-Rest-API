package com.pinkladydev.darts.game;

import org.junit.jupiter.api.Test;

import static com.pinkladydev.darts.chance.Chance.getRandomAlphaNumericString;
import static com.pinkladydev.darts.chance.Chance.getRandomNumberBetween;
import static com.pinkladydev.darts.game.HashGenerator.generateGamePlayerHash;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


class HashGeneratorTest {

    @Test
    void generateGamePlayerHash_shouldReturnConsistentHashForSameInputs() {
        final String gameId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));
        final String userId = getRandomAlphaNumericString(getRandomNumberBetween(15,20));

        final String hash1 = generateGamePlayerHash(gameId, userId);
        final String hash2 = generateGamePlayerHash(gameId, userId);

        assertEquals(hash1,hash2);
    }

    @Test
    void generateGamePlayerHash_shouldReturnUniqueHash() {
        final String hash1 = generateGamePlayerHash(
                getRandomAlphaNumericString(getRandomNumberBetween(15,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(15,20)));
        final String hash2 = generateGamePlayerHash(
                getRandomAlphaNumericString(getRandomNumberBetween(15,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(15,20)));

        assertNotEquals(hash1,hash2);
    }
}
