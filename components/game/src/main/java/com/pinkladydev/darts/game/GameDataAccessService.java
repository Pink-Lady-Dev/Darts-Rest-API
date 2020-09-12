package com.pinkladydev.darts.game;

import com.pinkladydev.darts.game.mappers.GamePlayerEntityToGamePlayerMapper;
import com.pinkladydev.darts.game.mappers.GamePlayerToGamePlayerEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pinkladydev.darts.game.HashGenerator.generateGamePlayerHash;
import static java.util.stream.Collectors.toList;

@Repository("MongoGame")
public class GameDataAccessService implements GameDao {

    private final GameRepository gameRepository;

    @Autowired
    public GameDataAccessService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GamePlayer getGamePlayer(String gameId, String gamePlayerId) {
        return GamePlayerEntityToGamePlayerMapper.map((
                gameRepository.findGamePlayerEntityById(generateGamePlayerHash(gamePlayerId, gameId))));
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
        gameRepository.save(
                Optional.ofNullable(GamePlayerToGamePlayerEntityMapper.map(game))
                        .orElseThrow(() -> new RuntimeException("No game player")));
    }
}
