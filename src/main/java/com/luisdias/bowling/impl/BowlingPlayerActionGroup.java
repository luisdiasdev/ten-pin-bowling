package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerActionGroup;

public class BowlingPlayerActionGroup implements PlayerActionGroup {

    private final int ALL_AVAILABLE_PINS = 10;

    private boolean isLastGroup = false;
    private ActionValue firstAction = ActionValue.INVALID;
    private ActionValue secondAction = ActionValue.INVALID;
    private ActionValue lastAction = ActionValue.INVALID;

    @Override
    public void addAction(ActionValue action) {
        if (!ActionValue.INVALID.equals(action) && canAddAnotherAction()) {
            setNextAction(action);
        }
    }

    private boolean canAddAnotherAction() {
        return ActionValue.INVALID.equals(firstAction)
            || ActionValue.INVALID.equals(secondAction)
            || canDoLastAction();
    }

    private boolean canDoLastAction() {
        return isLastGroup && ActionValue.INVALID.equals(lastAction);
    }

    private void setNextAction(ActionValue action) {
        if (ActionValue.INVALID.equals(firstAction)) {
            firstAction = action;
        } else if (ActionValue.INVALID.equals(secondAction)) {
            secondAction = action;
        } else if (canDoLastAction()) {
            lastAction = action;
        }
    }

    @Override
    public void setAsLastGroup() {
        isLastGroup = true;
    }

    @Override
    public boolean isSpare() {
        return !firstAction.isFoul()
            && firstAction.getValue() < ALL_AVAILABLE_PINS
            && (firstAction.getValue() + secondAction.getValue()) == ALL_AVAILABLE_PINS;
    }

    @Override
    public boolean isStrike() {
        return firstAction.getValue() == ALL_AVAILABLE_PINS;
    }

    @Override
    public boolean isComplete() {
        if (isLastGroup) {
            return isStrike() && hasTwoMoreValidActions();
        }
        return isStrike()
            || isSpare()
            || isNormalPoints();
    }

    private boolean hasTwoMoreValidActions() {
        return secondAction.getValue() >= 0 && lastAction.getValue() >= 0;
    }

    private boolean isNormalPoints() {
        if (firstAction.getValue() >= 0 && secondAction.getValue() >= 0) {
            int sum = firstAction.getValue() + secondAction.getValue();
            return sum >= 0 && sum < ALL_AVAILABLE_PINS;
        }
        return false;
    }
}
