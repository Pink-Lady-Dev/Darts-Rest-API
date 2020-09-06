package com.pinkladydev.darts.player.mappers;

import com.pinkladydev.darts.player.Player;
import com.pinkladydev.darts.player.PlayerEntity;

import static com.pinkladydev.darts.player.PlayerEntity.aPlayerEntityBuilder;

public class PlayerToPlayerEntityMapper {

    public static PlayerEntity map(Player player){
        return aPlayerEntityBuilder()
                .id(player.getId())
                .username(player.getUsername())
                .gameLog(player.getGameLog())
                .leagues(player.getLeagues())
                .build();
    }
}
