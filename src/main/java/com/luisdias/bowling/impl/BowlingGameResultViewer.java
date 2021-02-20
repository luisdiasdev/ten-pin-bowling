package com.luisdias.bowling.impl;

import com.luisdias.bowling.*;
import io.vavr.control.Either;
import java.io.File;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "result-viewer")
public class BowlingGameResultViewer implements GameResultViewer {

    @Parameters(index = "0", description = "The file containing the results of the bowling game")
    private File file;

    private final GameResultFileReader gameResultFileReader;
    private final PlayerActionParser playerActionParser;
    private final Game game;

    public BowlingGameResultViewer(
        GameResultFileReader gameResultFileReader,
        PlayerActionParser playerActionParser,
        Game game) {
        this.gameResultFileReader = gameResultFileReader;
        this.playerActionParser = playerActionParser;
        this.game = game;
    }

    @Override
    public Integer call() {
        gameResultFileReader.read(file, this::processLine);
        game.printResult();
        return 0;
    }

    private boolean processLine(String line) {
        Either<ParsingResultError, PlayerAction> actionParseResult = playerActionParser.parse(line);
        if (actionParseResult.isLeft()) {
            System.out.println(actionParseResult.mapLeft(ParsingResultError::getMessage).getLeft());
            return false;
        }
        PlayerAction action = actionParseResult.get();
        boolean wasActionValid = game.doPlayerAction(action);
        // if is left, print the error to the console
        // if is right, doPlayerAction and checkStatus afterwards
        return wasActionValid;
    }
}
