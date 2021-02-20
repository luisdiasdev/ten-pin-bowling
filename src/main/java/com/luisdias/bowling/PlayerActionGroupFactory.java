package com.luisdias.bowling;

public interface PlayerActionGroupFactory {

    PlayerActionGroup createPlayerActionGroup(ActionValue firstAction, ActionValue secondAction);
}
