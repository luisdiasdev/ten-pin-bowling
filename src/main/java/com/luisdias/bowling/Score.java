package com.luisdias.bowling;

public interface Score {

    int accumulatedScore();

    boolean isLast();

    boolean isStrike();

    boolean isSpare();

    int[] foulIndexes();

    int[] values();
}
