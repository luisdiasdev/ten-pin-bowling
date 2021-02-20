package com.luisdias.bowling;

import java.util.Objects;

public class ActionValue {

    private final Integer value;
    private final boolean isFoul;

    public ActionValue(Integer value, boolean isFoul) {
        this.value = value;
        this.isFoul = isFoul;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isFoul() {
        return isFoul;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActionValue that = (ActionValue) o;
        return isFoul == that.isFoul && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, isFoul);
    }

    public static ActionValue nullAction() {
        return new ActionValue(-1, true);
    }
}
