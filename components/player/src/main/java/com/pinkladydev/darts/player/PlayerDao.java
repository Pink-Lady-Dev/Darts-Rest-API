package com.pinkladydev.darts.player;

import java.util.List;

public interface PlayerDao {

    void insertPlayer(String username);

    void updatePlayer(Player player);

    List<Player> getAllPlayers();

    Player getPlayer(String userId);
}
