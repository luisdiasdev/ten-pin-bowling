package com.luisdias.bowling.impl;

import com.luisdias.bowling.Score;

public class FrameScore implements Score {

    private final int accumulatedScore;
    private final boolean last;
    private final boolean strike;
    private final boolean spare;
    private final int[] foulIndexes;
    private final int[] values;

    public FrameScore(
        int accumulatedScore,
        boolean last,
        boolean strike,
        boolean spare,
        int[] foulIndexes,
        int[] values) {
        this.accumulatedScore = accumulatedScore;
        this.last = last;
        this.strike = strike;
        this.spare = spare;
        this.foulIndexes = foulIndexes;
        this.values = values;
    }

    @Override
    public int accumulatedScore() {
        return accumulatedScore;
    }

    @Override
    public boolean isLast() {
        return last;
    }

    @Override
    public boolean isStrike() {
        return strike;
    }

    @Override
    public boolean isSpare() {
        return spare;
    }

    @Override
    public int[] foulIndexes() {
        return foulIndexes;
    }

    @Override
    public int[] values() {
        return values;
    }
}
