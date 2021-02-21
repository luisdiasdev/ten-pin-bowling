package com.luisdias.bowling.it;

import com.luisdias.bowling.Game;
import com.luisdias.bowling.GameResultProcessor;
import com.luisdias.bowling.PlayerActionParser;
import com.luisdias.bowling.PlayerFactory;
import com.luisdias.bowling.impl.*;
import java.io.File;

public class Bootstrap {

    public GameResultProcessor createProcessor(File inputFile) {
        return new BowlingGameResultProcessor(
            inputFile,
            new BowlingResultFileReader(),
            new BowlingResultConsoleOutputWriter(),
            createPlayerActionParser(),
            createGame());
    }

    PlayerActionParser createPlayerActionParser() {
        return new BowlingPlayerActionParser(new BowlingPlayerActionFactory());
    }

    Game createGame() {
        return new BowlingGame(
            createPlayerFactory(),
            new BowlingHeaderProvider(),
            new BowlingScoreCalculator(),
            new BowlingScoreConverter());
    }

    PlayerFactory createPlayerFactory() {
        return new BowlingPlayerFactory(new BowlingPlayerActionGroupFactory());
    }
}
