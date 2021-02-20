package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerAction;
import java.util.Objects;

public class BowlingPlayerAction implements PlayerAction {

    private final String playerName;
    private final ActionValue action;

    public BowlingPlayerAction(String playerName, ActionValue action) {
        this.playerName = playerName;
        this.action = action;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public ActionValue getValue() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BowlingPlayerAction that = (BowlingPlayerAction) o;
        return playerName.equals(that.playerName) && action.equals(that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, action);
    }
}
