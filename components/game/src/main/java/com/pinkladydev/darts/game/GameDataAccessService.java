package com.pinkladydev.darts.game;

import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository("MongoGame")
public class GameDataAccessService implements GameDao {

    private final GameRepository gameRepository;
    private final MessageDigest messageDigest;

    public GameDataAccessService(final GameRepository gameRepository) throws NoSuchAlgorithmException {
        this.gameRepository = gameRepository;
        this.messageDigest = MessageDigest.getInstance("SHA-256");
    }

    @Override
    public GamePlayer getGamePlayer(String gameId, String gamePlayerId) {
        // TODO break this into its own class
        final String hash = toHexString(this.messageDigest.digest(
                (gameId + gamePlayerId).getBytes(StandardCharsets.UTF_8)));

        return GamePlayerEntityToGamePlayerMapper.map((gameRepository.findGamePlayerEntityById(hash)));
    }

    @Override
    public List<GamePlayer> getGamePlayers(String gameId) {
        return gameRepository.findAllByGameId(gameId)
                .stream()
                .map(GamePlayerEntityToGamePlayerMapper::map)
                .collect(toList());
    }

    @Override
    public Game getTotalGame(String gameId) {
        List<GamePlayer> gamePlayers = getGamePlayers(gameId);

        GameType gameType = gamePlayers.stream()
                .map(GamePlayer::getGameType)
                .reduce((id1, id2) -> id2.equals(id1) ? id2 : GameType.NULL)
                .filter(id -> !GameType.NULL.equals(id))
                .orElseThrow(() -> new RuntimeException("Mismatched Game Types"));

        return Game.aGameBuilder()
                .id(gameId)
                .gamePlayers(gamePlayers)
                .gameType(gameType)
                .build();
    }

    @Override
    public void save(GamePlayer game) {
        gameRepository.save(GamePlayerToGamePlayerEntityMapper.map(game));
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
