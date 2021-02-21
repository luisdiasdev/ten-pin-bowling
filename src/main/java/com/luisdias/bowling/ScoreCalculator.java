package com.luisdias.bowling;

import java.util.List;

public interface ScoreCalculator {

    List<Score> calculate(List<PlayerActionGroup> playerActionGroups);
}
