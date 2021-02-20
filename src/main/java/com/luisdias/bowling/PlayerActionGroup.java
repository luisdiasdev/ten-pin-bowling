package com.luisdias.bowling;

public interface PlayerActionGroup {

    void setAsLastGroup();

    void addAction(ActionValue action);

    boolean isSpare();

    boolean isStrike();

    boolean isComplete();
}
