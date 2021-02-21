package com.luisdias.bowling.impl;

import com.luisdias.bowling.*;
import io.vavr.control.Either;
import java.io.File;

public class BowlingGameResultProcessor implements GameResultProcessor {

    private final File inputFile;
    private final GameResultFileReader gameResultFileReader;
    private final ResultOutputWriter resultOutputWriter;
    private final PlayerActionParser playerActionParser;
    private final Game game;

    public BowlingGameResultProcessor(
        File inputFile,
        GameResultFileReader gameResultFileReader,
        ResultOutputWriter resultOutputWriter,
        PlayerActionParser playerActionParser,
        Game game) {
        this.inputFile = inputFile;
        this.gameResultFileReader = gameResultFileReader;
        this.resultOutputWriter = resultOutputWriter;
        this.playerActionParser = playerActionParser;
        this.game = game;
    }

    @Override
    public Integer process() {
        gameResultFileReader.read(inputFile, this::processLine);
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
