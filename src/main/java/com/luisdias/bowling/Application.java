package com.luisdias.bowling;

import com.luisdias.bowling.impl.*;
import picocli.CommandLine;

public class Application {

    public static void main(String[] args) {
        GameResultViewer gameResultViewer = new BowlingGameResultViewer(
            new BowlingResultFileReader(),
            createPlayerActionParser(),
            new BowlingGame());

        int exitCode = new CommandLine(gameResultViewer).execute(args);
        System.exit(exitCode);
    }

    private static PlayerActionParser createPlayerActionParser() {
        return new BowlingPlayerActionParser(new BowlingPlayerActionFactory());
    }
}
