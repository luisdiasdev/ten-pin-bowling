package com.luisdias.bowling.impl;

import com.luisdias.bowling.PlayerActionGroup;
import com.luisdias.bowling.Score;
import com.luisdias.bowling.ScoreCalculator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BowlingScoreCalculator implements ScoreCalculator {

    private static final int STRIKE_BONUS = 2;
    private static final int SPARE_BONUS = 1;

    @Override
    public List<Score> calculate(List<PlayerActionGroup> playerActionGroups) {
        int totalFrames = playerActionGroups.size();
        List<Score> scores = new ArrayList<>();
        int totalScore = 0;

        for (int i = 0; i < totalFrames; i++) {
            PlayerActionGroup actionGroup = playerActionGroups.get(i);
            int bonus = getCurrentBonus(actionGroup);
            int[] currentValues = actionGroup.getValues();
            int frameScore = Arrays.stream(currentValues).sum();
            boolean isLastFrame = i == totalFrames - 1;

            if (!isLastFrame) {
                int bonusResult = getBonusResult(playerActionGroups, i, bonus);

                frameScore += bonusResult;
            }
            totalScore += frameScore;

            scores.add(new FrameScore(
                totalScore,
                isLastFrame,
                actionGroup.isStrike(),
                actionGroup.isSpare(),
                actionGroup.getFoulIndexes(),
                currentValues
            ));
        }

        return scores;
    }

    private int getBonusResult(List<PlayerActionGroup> playerActionGroups, int i, int bonus) {
        AtomicInteger bonusRolls = new AtomicInteger(bonus);
        return getNextActionGroups(playerActionGroups, i, bonus)
            .stream()
            .mapToInt(nextGroup -> calculateBonus(bonusRolls, nextGroup))
            .reduce(Integer::sum)
            .orElse(0);
    }

    private int calculateBonus(AtomicInteger bonusRolls, PlayerActionGroup nextGroup) {
        int[] values = nextGroup.getValues();

        int sum = 0;
        for (int value : values) {
            if (bonusRolls.get() <= 0) {
                break;
            }
            sum += value;
            bonusRolls.getAndDecrement();
        }
        return sum;
    }

    private int getCurrentBonus(PlayerActionGroup actionGroup) {
        int bonus = 0;
        if (actionGroup.isStrike()) {
            bonus = STRIKE_BONUS;
        } else if (actionGroup.isSpare()) {
            bonus = SPARE_BONUS;
        }
        return bonus;
    }

    private List<PlayerActionGroup> getNextActionGroups(
        List<PlayerActionGroup> playerActionGroups,
        int currentIndex,
        int nextGroupCount) {
        if (nextGroupCount <= 0) {
            return Collections.emptyList();
        }
        int nextIndex = currentIndex + nextGroupCount+1;
        if (nextIndex < playerActionGroups.size()) {
            return playerActionGroups.subList(currentIndex+1, nextIndex);
        }
        int lowerBound = Math.min(currentIndex+1, playerActionGroups.size() - 1);
        return playerActionGroups.subList(lowerBound, playerActionGroups.size());
    }
}
