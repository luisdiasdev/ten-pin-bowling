package com.luisdias.bowling.impl;

import com.luisdias.bowling.Score;
import com.luisdias.bowling.ScoreConverter;
import java.util.*;
import java.util.stream.Collectors;

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
        String pinfallsString = scores.stream()
            .map(score -> {
                if (score.isStrike() && !score.isLast()) {
                    return "\tX\t";
                }
                int[] values = score.values();
                if (score.isSpare()) {
                    return String.valueOf(values[0]).concat("\t/\t");
                }
                prepareFouls(score, values);
                return getNormalScoreString(values);
            })
            .collect(Collectors.joining());

        StringBuilder pinfallsSb = new StringBuilder("Pinfalls\t");
        pinfallsSb.append(pinfallsString);
        pinfallsSb.setLength(pinfallsSb.length() - 1);
        pinfallsSb.append("\n");
        return pinfallsSb.toString();
    }

    private String getNormalScoreString(int[] values) {
        return Arrays.stream(values)
            .mapToObj(this::getSingleValueAsString)
            .map(s -> s.concat("\t"))
            .collect(Collectors.joining(""));
    }

    private String getSingleValueAsString(int value) {
        if (value == -1) {
            return "F";
        } else {
            if (value == 10) {
                return "X";
            } else {
                return String.valueOf(value);
            }
        }
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
        String scoresString = scores.stream()
            .map(score -> String.valueOf(score.accumulatedScore()))
            .collect(Collectors.joining("\t\t"));
        return "Score\t\t" + scoresString + "\n";
    }
}
