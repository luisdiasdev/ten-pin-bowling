package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.Player;
import com.luisdias.bowling.PlayerActionGroup;
import com.luisdias.bowling.PlayerActionGroupFactory;
import java.util.ArrayList;
import java.util.List;

public class BowlingPlayer implements Player {

    private static final int LAST_FRAME = 9;
    private static final int MAX_ROLLS = 21;

    private final String name;
    private final PlayerActionGroupFactory playerActionGroupFactory;
    private final List<PlayerActionGroup> completedFrames;
    private int rollCount = 0;
    private PlayerActionGroup currentActionGroup;

    public BowlingPlayer(String name, PlayerActionGroupFactory playerActionGroupFactory) {
        this.name = name;
        this.playerActionGroupFactory = playerActionGroupFactory;
        this.completedFrames = new ArrayList<>();
        this.currentActionGroup = playerActionGroupFactory.createPlayerActionGroup();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean doAction(ActionValue nextAction) {
        if (completedFrames.size() > LAST_FRAME) {
            System.out.println("Trying to do more than 10 frames");
            return false;
        }
        if (rollCount >= MAX_ROLLS) {
            System.out.println("Trying to do more actions than the maximum allowed of" + MAX_ROLLS);
            return false;
        }

        if (!currentActionGroup.isComplete()) {
            currentActionGroup.addAction(nextAction);
        }

        if (currentActionGroup.isComplete()) {
            completedFrames.add(currentActionGroup);
            currentActionGroup = createNextActionGroup();
        }
        rollCount++;
        return true;
    }

    private PlayerActionGroup createNextActionGroup() {
        PlayerActionGroup actionGroup = playerActionGroupFactory.createPlayerActionGroup();
        if (isLastFrame()) {
            actionGroup.setAsLastGroup();
        }
        return actionGroup;
    }

    private boolean isLastFrame() {
        return completedFrames.size() == LAST_FRAME;
    }

    @Override
    public List<PlayerActionGroup> getCompletedActionGroups() {
        return completedFrames;
    }
}
