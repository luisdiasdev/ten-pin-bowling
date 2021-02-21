package com.luisdias.bowling;

import com.luisdias.bowling.impl.*;
import java.io.File;
import picocli.CommandLine;

public class Application {

    public static void main(String[] args) {
        GameResultViewer gameResultViewer = new BowlingGameResultViewer(
            new BowlingResultFileReader(),
            new BowlingResultFileOutputWriter(new File("./output.txt")),
            createPlayerActionParser(),
            createGame());

        int exitCode = new CommandLine(gameResultViewer).execute(args);
        System.exit(exitCode);
    }

    private static PlayerActionParser createPlayerActionParser() {
        return new BowlingPlayerActionParser(new BowlingPlayerActionFactory());
    }

    private static Game createGame() {
        return new BowlingGame(
            createPlayerFactory(),
            new BowlingHeaderProvider(),
            new BowlingScoreCalculator(),
            new BowlingScoreConverter());
    }

    private static PlayerFactory createPlayerFactory() {
        return new BowlingPlayerFactory(new BowlingPlayerActionGroupFactory());
    }
}
