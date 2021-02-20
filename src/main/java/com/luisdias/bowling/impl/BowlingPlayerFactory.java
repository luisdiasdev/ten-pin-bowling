package com.luisdias.bowling.impl;

import com.luisdias.bowling.Player;
import com.luisdias.bowling.PlayerActionGroupFactory;
import com.luisdias.bowling.PlayerFactory;

public class BowlingPlayerFactory implements PlayerFactory {

    private final PlayerActionGroupFactory playerActionGroupFactory;

    public BowlingPlayerFactory(PlayerActionGroupFactory playerActionGroupFactory) {
        this.playerActionGroupFactory = playerActionGroupFactory;
    }

    @Override
    public Player createPlayer(String playerName) {
        return new BowlingPlayer(playerName, playerActionGroupFactory);
    }
}
