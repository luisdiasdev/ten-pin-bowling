package com.luisdias.bowling.impl;

import com.luisdias.bowling.PlayerActionGroup;
import com.luisdias.bowling.PlayerActionGroupFactory;

public class BowlingPlayerActionGroupFactory implements PlayerActionGroupFactory {

    @Override
    public PlayerActionGroup createPlayerActionGroup() {
        return new BowlingPlayerActionGroup();
    }
}
