package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerActionGroup;
import java.util.stream.Stream;

public class BowlingPlayerActionGroup implements PlayerActionGroup {

    private static final int ALL_AVAILABLE_PINS = 10;

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
        return ((ActionValue.INVALID.equals(firstAction)
            || ActionValue.INVALID.equals(secondAction)) && !isStrike())
            || canDoLastAction();
    }

    private boolean canDoLastAction() {
        return isLastGroup && ActionValue.INVALID.equals(lastAction)
            && (isSpare() || isStrike());
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
            return ((isStrike() || isSpare()) && hasTwoMoreValidActions())
                || isNormalPoints();
        }
        return isStrike() || isSpare() || isNormalPoints();
    }

    @Override
    public int[] getValues() {
        return getValuesStream()
            .mapToInt(ActionValue::getValue)
            .filter(this::greaterOrEqualToZero)
            .toArray();
    }

    @Override
    public int[] getFoulIndexes() {
        return getValuesStream()
            .map(ActionValue::isFoul)
            .mapToInt(isFoul -> Boolean.TRUE.equals(isFoul) ? 1 : 0)
            .toArray();
    }

    private Stream<ActionValue> getValuesStream() {
        if (isLastGroup) {
            return Stream.of(firstAction, secondAction, lastAction);
        }
        return Stream.of(firstAction, secondAction);
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

    private boolean greaterOrEqualToZero(int i) {
        return i >= 0;
    }
}
