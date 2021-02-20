package com.luisdias.bowling;

public interface PlayerActionFactory {

    PlayerAction createPlayerAction(String playerName, Integer action);
}
