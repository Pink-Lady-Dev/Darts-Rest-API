package com.pinkladydev.darts.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pinkladydev.darts.player.PlayerEntity.newPlayerEntity;
import static java.util.stream.Collectors.toList;

@Repository("PlayerMongo")
public class PlayerDataService implements PlayerDao{

    final PlayerRepository playerRepository;

    @Autowired
    public PlayerDataService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void insertPlayer(String username) {
        playerRepository.save(newPlayerEntity(username));
    }

    @Override
    public void updatePlayer(Player player) {
        playerRepository.save(PlayerToPlayerEntityMapper.map(player));
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll()
                .stream()
                .map(PlayerEntityToPlayerMapper::map)
                .collect(toList());
    }

    @Override
    public Player getPlayer(String userId) {
        return PlayerEntityToPlayerMapper.map(playerRepository.findByUsername(userId));
    }
}
