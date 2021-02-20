package com.luisdias.bowling;

import java.util.List;

public interface PlayerActionGroup {

    void addAction(Integer action);

    boolean isSpare();

    boolean isStrike();

    boolean isComplete();
}
