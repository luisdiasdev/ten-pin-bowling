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
    private final ResultOutputWriter resultOutputWriter;
    private final PlayerActionParser playerActionParser;
    private final Game game;

    public BowlingGameResultViewer(
        GameResultFileReader gameResultFileReader,
        ResultOutputWriter resultOutputWriter,
        PlayerActionParser playerActionParser,
        Game game) {
        this.gameResultFileReader = gameResultFileReader;
        this.resultOutputWriter = resultOutputWriter;
        this.playerActionParser = playerActionParser;
        this.game = game;
    }

    @Override
    public Integer call() {
        gameResultFileReader.read(file, this::processLine);
        boolean wasWriteSuccessful = resultOutputWriter.write(game.generateResults());
        return wasWriteSuccessful ? 0 : 1;
    }

    private boolean processLine(String line) {
        Either<ParsingResultError, PlayerAction> actionParseResult = playerActionParser.parse(line);
        if (actionParseResult.isLeft()) {
            System.out.println(actionParseResult.mapLeft(ParsingResultError::getMessage).getLeft());
            return false;
        }
        PlayerAction action = actionParseResult.get();
        return game.doPlayerAction(action);
    }
}
