package com.luisdias.bowling.impl;

import com.luisdias.bowling.PlayerActionGroup;
import com.luisdias.bowling.Score;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BowlingScoreCalculatorTest {

    BowlingScoreCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new BowlingScoreCalculator();
    }

    PlayerActionGroup baseActionGroup() {
        PlayerActionGroup mockActionGroup = mock(PlayerActionGroup.class);
        when(mockActionGroup.isComplete()).thenReturn(true);
        return mockActionGroup;
    }

    PlayerActionGroup normalActionGroup(int... values) {
        PlayerActionGroup mockActionGroup = baseActionGroup();
        when(mockActionGroup.isSpare()).thenReturn(false);
        when(mockActionGroup.isStrike()).thenReturn(false);
        when(mockActionGroup.getValues()).thenReturn(values);
        return mockActionGroup;
    }

    PlayerActionGroup strikeActionGroup() {
        return strikeActionGroup(10);
    }

    PlayerActionGroup strikeActionGroup(int... values) {
        PlayerActionGroup mockActionGroup = baseActionGroup();
        when(mockActionGroup.isSpare()).thenReturn(false);
        when(mockActionGroup.isStrike()).thenReturn(true);
        when(mockActionGroup.getValues()).thenReturn(values);
        return mockActionGroup;
    }

    PlayerActionGroup spareActionGroup(int... values) {
        PlayerActionGroup mockActionGroup = baseActionGroup();
        when(mockActionGroup.isSpare()).thenReturn(true);
        when(mockActionGroup.isStrike()).thenReturn(false);
        when(mockActionGroup.getValues()).thenReturn(values);
        return mockActionGroup;
    }

    PlayerActionGroup allFoulsActionGroup() {
        PlayerActionGroup mockActionGroup = normalActionGroup(0, 0);
        when(mockActionGroup.getFoulIndexes()).thenReturn(new int[]{1, 1});
        return mockActionGroup;
    }

    PlayerActionGroup allStrikeActionGroup(boolean isLast) {
        PlayerActionGroup mockActionGroup = strikeActionGroup();
        when(mockActionGroup.getFoulIndexes()).thenReturn(new int[]{0, 0});
        if (isLast) {
            when(mockActionGroup.getValues()).thenReturn(new int[]{10, 10, 10});
        } else {
            when(mockActionGroup.getValues()).thenReturn(new int[]{10});
        }
        return mockActionGroup;
    }

    @DisplayName("All Zeroes")
    @Nested
    class AllZeroes {

        List<PlayerActionGroup> allZeroesActions = IntStream.range(0, 10)
            .mapToObj(i -> normalActionGroup(0, 0))
            .collect(Collectors.toList());

        @Test
        void shouldReturnAllZeroesScores() {
            List<Score> scores = calculator.calculate(allZeroesActions);

            assertThat(scores)
                .extracting(Score::accumulatedScore)
                .containsOnly(0);
        }
    }

    @DisplayName("Sample Input")
    @Nested
    class SampleInput {

        int targetScore = 167;

        List<PlayerActionGroup> sampleInputActions = Stream.of(
            strikeActionGroup(),
            spareActionGroup(7, 3),
            normalActionGroup(9, 0),
            strikeActionGroup(),
            normalActionGroup(0, 8),
            spareActionGroup(8, 2),
            normalActionGroup(0, 6),
            strikeActionGroup(),
            strikeActionGroup(),
            strikeActionGroup(10, 8, 1)
        ).collect(Collectors.toList());

        @Test
        void shouldReturnTargetScore() {
            List<Score> scores = calculator.calculate(sampleInputActions);

            assertThat(scores)
                .extracting(Score::accumulatedScore)
                .last()
                .isEqualTo(targetScore);
        }
    }

    @DisplayName("All Fouls")
    @Nested
    class AllFouls {

        List<PlayerActionGroup> allFoulsActions = IntStream.range(0, 10)
            .mapToObj(i -> allFoulsActionGroup())
            .collect(Collectors.toList());

        @Test
        void shouldZeroScore() {
            List<Score> scores = calculator.calculate(allFoulsActions);

            assertThat(scores)
                .extracting(Score::accumulatedScore)
                .last()
                .isEqualTo(0);
        }
    }

    @DisplayName("Perfect")
    @Nested
    class PerfectScore {

        int perfectScore = 300;
        List<PlayerActionGroup> allStrikesActions = IntStream.range(0, 10)
            .mapToObj(i -> allStrikeActionGroup(i == 9))
            .collect(Collectors.toList());

        @Test
        void shouldReturnPerfectScore() {
            List<Score> scores = calculator.calculate(allStrikesActions);

            assertThat(scores)
                .extracting(Score::accumulatedScore)
                .last()
                .isEqualTo(perfectScore);
        }
    }
}