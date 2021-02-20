package com.luisdias.bowling;

public interface Game {
    // A game consists of multiple players (1 <= n < infinity)
    // A game can have a minimum of 10 and a maximum of 12 frames
    //  - 12 frames if the last ball was a strike or a spare

    boolean doPlayerAction(PlayerAction action);
}
