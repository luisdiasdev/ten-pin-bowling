package com.luisdias.bowling;

public interface Player {

    String getName();

    boolean doAction(ActionValue value);

    Integer getCurrentScore();
}
