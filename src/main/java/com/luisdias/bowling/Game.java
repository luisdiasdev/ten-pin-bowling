package com.luisdias.bowling;

import java.util.List;

public interface Game {

    boolean doPlayerAction(PlayerAction action);

    List<String> generateResults();
}
