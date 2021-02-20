package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerActionGroup;

public class BowlingPlayerActionGroup implements PlayerActionGroup {

    private final ActionValue firstAction;
    private final ActionValue secondAction;

    public BowlingPlayerActionGroup(ActionValue firstAction, ActionValue secondAction) {
        this.firstAction = firstAction;
        this.secondAction = secondAction;
    }

    @Override
    public void addAction(Integer action) {

    }

    @Override
    public boolean isSpare() {
        // return true if the first action is < 10
        // and the second action + first = 10
        return false;
    }

    @Override
    public boolean isStrike() {
        // return true if the first action is = 10
        return false;
    }

    @Override
    public boolean isComplete() {
        // return true if is either spare or strike
        // of if it has two actions but neither spare or strike
        return false;
    }
}
