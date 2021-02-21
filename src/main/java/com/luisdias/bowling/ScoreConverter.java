package com.luisdias.bowling;

import java.util.List;

public interface ScoreConverter {

    List<String> convert(String playerName, List<Score> score);
}
