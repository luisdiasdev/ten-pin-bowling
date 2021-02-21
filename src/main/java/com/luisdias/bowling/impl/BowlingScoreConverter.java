package com.luisdias.bowling.impl;

import com.luisdias.bowling.Score;
import com.luisdias.bowling.ScoreConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.luisdias.bowling.helper.StringBuilderHelper.appendIfFalse;

public class BowlingScoreConverter implements ScoreConverter {

    @Override
    public List<String> convert(String playerName, List<Score> scores) {
        if (Objects.isNull(scores) || scores.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> convertedLines = new ArrayList<>();
        convertedLines.add(generatePlayerNameString(playerName));
        convertedLines.add(generatePinfallsString(scores));
        convertedLines.add(generateScoreString(scores));
        return convertedLines;
    }

    private String generatePlayerNameString(String playerName) {
        return playerName + "\n";
    }

    private String generatePinfallsString(List<Score> scores) {
        StringBuilder pinfallsSb = new StringBuilder("Pinfalls\t");

        scores.forEach(score -> {
            if (score.isStrike() && !score.isLast()) {
                pinfallsSb.append("\tX\t");
                return;
            }
            int[] values = score.values();
            if (score.isSpare()) {
                pinfallsSb
                    .append(values[0])
                    .append("\t/\t");
                return;
            }
            prepareFouls(score, values);
            for (int value : values) {
                if (value == -1) {
                    pinfallsSb.append("F");
                } else {
                    if (value == 10) {
                        pinfallsSb.append("X");
                    } else {
                        pinfallsSb.append(value);
                    }
                }
                pinfallsSb.append("\t");
            }

        });
        pinfallsSb.append("\n");
        return pinfallsSb.toString();
    }

    private void prepareFouls(Score score, int[] values) {
        int[] foulIndexes = score.foulIndexes();
        int foulIndexesLength = foulIndexes.length;
        for (int i = 0; i < foulIndexesLength; i++) {
            if (foulIndexes[i] == 1) {
                values[i] = -1;
            }
        }
    }

    private String generateScoreString(List<Score> scores) {
        StringBuilder scoreSb = new StringBuilder("Score\t\t");
        scores.forEach(score -> {

            scoreSb.append(score.accumulatedScore());
            appendIfFalse(scoreSb, "\t\t", score.isLast());
        });
        scoreSb.append("\n");
        return scoreSb.toString();
    }
}
