package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerAction;
import com.luisdias.bowling.PlayerActionFactory;

public class BowlingPlayerActionFactory implements PlayerActionFactory {

    @Override
    public PlayerAction createPlayerAction(String playerName, ActionValue action) {
        return new BowlingPlayerAction(playerName, action);
    }
}
