package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.Player;
import com.luisdias.bowling.PlayerActionGroup;
import com.luisdias.bowling.PlayerActionGroupFactory;
import java.util.ArrayList;
import java.util.List;

public class BowlingPlayer implements Player {

    private static final int MAX_ROLLS = 21; // 2 per frame + 1 for the last

    private final String name;
    private final PlayerActionGroupFactory playerActionGroupFactory;

    private int rollCount = 0;
    private List<PlayerActionGroup> finishedFrames;
    private ActionValue currentAction = ActionValue.nullAction();

    public BowlingPlayer(String name, PlayerActionGroupFactory playerActionGroupFactory) {
        this.name = name;
        this.playerActionGroupFactory = playerActionGroupFactory;
        this.finishedFrames = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void doAction(ActionValue nextAction) {
        if (currentAction.getValue() >= 0) {
            finishedFrames.add(playerActionGroupFactory.createPlayerActionGroup(
                currentAction,
                nextAction
            ));
        }
        rollCount += 1;
    }

    @Override
    public Integer getCurrentScore() {
        return rollCount; // TEMPORARY
    }
}
