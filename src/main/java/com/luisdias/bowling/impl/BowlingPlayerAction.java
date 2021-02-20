package com.luisdias.bowling.impl;

import com.luisdias.bowling.PlayerAction;
import java.util.Objects;

public class BowlingPlayerAction implements PlayerAction {

    private final String playerName;
    private final Integer action;

    public BowlingPlayerAction(String playerName, Integer action) {
        this.playerName = playerName;
        this.action = action;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public Integer getAction() {
        return action;
    }

    @Override
    public boolean isValid() {
        // Check if action is between 0 and 10
        return false;
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
