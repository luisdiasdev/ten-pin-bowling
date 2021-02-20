package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerActionGroup;
import com.luisdias.bowling.PlayerActionGroupFactory;

public class BowlingPlayerActionGroupFactory implements PlayerActionGroupFactory {

    @Override
    public PlayerActionGroup createPlayerActionGroup(ActionValue firstAction, ActionValue secondAction) {
        return new BowlingPlayerActionGroup(firstAction, secondAction);
    }
}
