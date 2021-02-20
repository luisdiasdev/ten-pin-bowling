package com.luisdias.bowling;

public interface Player {

    String getName();

    void doAction(ActionValue value);

    Integer getCurrentScore();
}
