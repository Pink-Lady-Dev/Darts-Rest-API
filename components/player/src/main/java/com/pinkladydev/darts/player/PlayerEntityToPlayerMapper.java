package com.pinkladydev.darts.player;

import static com.pinkladydev.darts.player.Player.aPlayerBuilder;

public class PlayerEntityToPlayerMapper {

    public static Player map(PlayerEntity player){
        return aPlayerBuilder()
                .id(player.getId())
                .username(player.getUsername())
                .gameLog(player.getGameLog())
                .leagues(player.getLeagues())
                .build();
    }
}
