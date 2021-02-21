package com.luisdias.bowling.impl;

import com.luisdias.bowling.Score;
import java.util.Collections;
import java.util.List;
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

class BowlingScoreConverterTest {

    BowlingScoreConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BowlingScoreConverter();
    }

    Score baseScore(int currentScore, boolean isLast, boolean isStrike, boolean isSpare) {
        Score score = mock(Score.class);
        when(score.accumulatedScore()).thenReturn(currentScore);
        when(score.isLast()).thenReturn(isLast);
        when(score.isStrike()).thenReturn(isStrike);
        when(score.isSpare()).thenReturn(isSpare);
        return score;
    }

    Score zeroScore(boolean isLast) {
        Score score = baseScore(0, isLast, false, false);
        when(score.foulIndexes()).thenReturn(new int[]{0, 0});
        when(score.values()).thenReturn(new int[]{0, 0});
        return score;
    }

    Score perfectScore(int currentScore, boolean isLast) {
        Score score = baseScore(currentScore, isLast, true, false);
        when(score.foulIndexes()).thenReturn(new int[]{0, 0});
        int[] values = new int[]{10, 10};
        if (isLast) {
            values = new int[]{10, 10, 10};
        }
        when(score.values()).thenReturn(values);
        return score;
    }

    Score normalScore(int currentScore, boolean isLast, int... values) {
        Score score = baseScore(currentScore, isLast, false, false);
        when(score.foulIndexes()).thenReturn(new int[]{0, 0});
        when(score.values()).thenReturn(values);
        return score;
    }

    Score spareScore(int currentScore, boolean isLast, int... values) {
        Score score = baseScore(currentScore, isLast, false, true);
        when(score.foulIndexes()).thenReturn(new int[]{0, 0});
        when(score.values()).thenReturn(values);
        return score;
    }

    Score strikeScore(int currentScore, boolean isLast, int... values) {
        Score score = baseScore(currentScore, isLast, true, false);
        when(score.foulIndexes()).thenReturn(new int[]{0, 0});
        when(score.values()).thenReturn(values);
        return score;
    }

    Score strikeScore(int currentScore, boolean isLast) {
        return strikeScore(currentScore, isLast, 10);
    }

    Score foulScore(int currentScore, boolean isLast, int[] values, int[] foulIndexes) {
        Score score = baseScore(currentScore, isLast, false, false);
        when(score.foulIndexes()).thenReturn(foulIndexes);
        when(score.values()).thenReturn(values);
        return score;
    }

    Score foulScore(int currentScore) {
        return foulScore(currentScore, false, new int[]{0, 0}, new int[]{1, 1});
    }

    @DisplayName("Empty Input")
    @Nested
    class EmptyInput {

        String playerName = "Amy";

        @Test
        void shouldReturnEmptyListIfInputIsNull() {
            List<String> result = converter.convert(playerName, null);

            assertThat(result).isEmpty();
        }

        @Test
        void shouldReturnEmptyListIfInputIsEmpty() {
            List<String> result = converter.convert(playerName, Collections.emptyList());

            assertThat(result).isEmpty();
        }
    }

    @DisplayName("All Fouls")
    @Nested
    class AllFouls {

        String playerName = "Clint";

        List<Score> allFoulsScores = IntStream.range(0, 10)
            .mapToObj(i -> foulScore(0))
            .collect(Collectors.toList());

        @Test
        void shouldGenerateFirstLineWithPlayerName() {
            List<String> resultStrings = converter.convert(playerName, allFoulsScores);

            assertThat(resultStrings.get(0))
                .isEqualTo(playerName.concat("\n"));
        }

        @Test
        void shouldGenerateSecondLineWithPinfallsString() {
            List<String> resultStrings = converter.convert(playerName, allFoulsScores);

            assertThat(resultStrings.get(1))
                .isEqualTo("Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\t\n");
        }

        @Test
        void shouldGenerateThirdLineWithScoreString() {
            List<String> resultStrings = converter.convert(playerName, allFoulsScores);

            assertThat(resultStrings.get(2))
                .isEqualTo("Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t\n");
        }
    }

    @DisplayName("Sample Input")
    @Nested
    class SampleInput {

        String playerName = "Joseph";

        List<Score> sampleInputScores = Stream.of(
            strikeScore(20, false),
            spareScore(39, false, 7, 3),
            normalScore(48, false, 9, 0),
            strikeScore(66, false),
            normalScore(74, false, 0, 8),
            spareScore(84, false, 8, 2),
            foulScore(90, false, new int[]{0, 6}, new int[]{1, 0}),
            strikeScore(120, false),
            strikeScore(148, false),
            strikeScore(167, true, 10, 8, 1)
        ).collect(Collectors.toList());

        @Test
        void shouldGenerateFirstLineWithPlayerName() {
            List<String> resultStrings = converter.convert(playerName, sampleInputScores);

            assertThat(resultStrings.get(0))
                .isEqualTo(playerName.concat("\n"));
        }

        @Test
        void shouldGenerateSecondLineWithPinfallsString() {
            List<String> resultStrings = converter.convert(playerName, sampleInputScores);

            assertThat(resultStrings.get(1))
                .isEqualTo("Pinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1\n");
        }

        @Test
        void shouldGenerateThirdLineWithScoreString() {
            List<String> resultStrings = converter.convert(playerName, sampleInputScores);

            assertThat(resultStrings.get(2))
                .isEqualTo("Score\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167\n");
        }
    }

    @DisplayName("Perfect")
    @Nested
    class Perfect {

        String playerName = "Joe";

        List<Score> perfectScores = IntStream.iterate(30, i -> i + 30)
            .limit(10)
            .mapToObj(i -> perfectScore(i, i == 300))
            .collect(Collectors.toList());

        @Test
        void shouldGenerateFirstLineWithPlayerName() {
            List<String> resultStrings = converter.convert(playerName, perfectScores);

            assertThat(resultStrings.get(0))
                .isEqualTo(playerName.concat("\n"));
        }

        @Test
        void shouldGenerateSecondLineWithPinfallsString() {
            List<String> resultStrings = converter.convert(playerName, perfectScores);

            assertThat(resultStrings.get(1))
                .isEqualTo("Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX\n");
        }

        @Test
        void shouldGenerateThirdLineWithScoreString() {
            List<String> resultStrings = converter.convert(playerName, perfectScores);

            assertThat(resultStrings.get(2))
                .isEqualTo("Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n");
        }
    }

    @DisplayName("All Zeroes")
    @Nested
    class AllZeroes {

        String playerName = "James";

        List<Score> allZeroesScores = IntStream.range(0, 10)
            .mapToObj(i -> zeroScore(i == 9))
            .collect(Collectors.toList());

        @Test
        void shouldGenerateFirstLineWithPlayerName() {
            List<String> resultStrings = converter.convert(playerName, allZeroesScores);

            assertThat(resultStrings.get(0))
                .isEqualTo(playerName.concat("\n"));
        }

        @Test
        void shouldGenerateSecondLineWithPinfallsString() {
            List<String> resultStrings = converter.convert(playerName, allZeroesScores);

            assertThat(resultStrings.get(1))
                .isEqualTo("Pinfalls\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\n");
        }

        @Test
        void shouldGenerateThirdLineWithScoreString() {
            List<String> resultStrings = converter.convert(playerName, allZeroesScores);

            assertThat(resultStrings.get(2))
                .isEqualTo("Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n");
        }
    }
}