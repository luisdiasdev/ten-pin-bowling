package com.luisdias.bowling.impl;

import com.luisdias.bowling.*;
import io.vavr.control.Either;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BowlingGameResultProcessor implements GameResultProcessor {

    private final File inputFile;
    private final GameResultFileReader gameResultFileReader;
    private final ResultOutputWriter resultOutputWriter;
    private final PlayerActionParser playerActionParser;
    private final ResultValidator resultValidator;
    private final Game game;

    public BowlingGameResultProcessor(
        File inputFile,
        GameResultFileReader gameResultFileReader,
        ResultOutputWriter resultOutputWriter,
        ResultValidator resultValidator,
        PlayerActionParser playerActionParser,
        Game game) {
        this.inputFile = inputFile;
        this.gameResultFileReader = gameResultFileReader;
        this.resultOutputWriter = resultOutputWriter;
        this.playerActionParser = playerActionParser;
        this.resultValidator = resultValidator;
        this.game = game;
    }

    @Override
    public Integer process() {
        if (!gameResultFileReader.read(inputFile, this::processLine)) {
            return logErrorAndReturn();
        }
        List<String> results = game.generateResults();
        if (!resultValidator.isValid(results)) {
            return logErrorAndReturn();
        }
        resultOutputWriter.write(results);
        return 0;
    }

    private int logErrorAndReturn() {
        System.out.println("Could not process the given input file.");
        return 1;
    }

    private boolean processLine(String line) {
        Either<ParsingResultError, PlayerAction> actionParseResult = playerActionParser.parse(line);
        if (actionParseResult.isLeft()) {
            return false;
        }
        PlayerAction action = actionParseResult.get();
        return game.doPlayerAction(action);
    }
}
