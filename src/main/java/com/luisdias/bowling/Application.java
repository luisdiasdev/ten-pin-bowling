package com.luisdias.bowling;

import com.luisdias.bowling.cli.ApplicationCommand;
import com.luisdias.bowling.impl.*;
import java.io.File;
import picocli.CommandLine;

public class Application {

    private static final String DEFAULT_OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        ApplicationCommand applicationCommand = new ApplicationCommand((command) -> {
            GameResultProcessor processor = createProcessor(command);
            return processor.process();
        });
        int exitCode = new CommandLine(applicationCommand).execute(args);
        System.exit(exitCode);
    }

    private static GameResultProcessor createProcessor(ApplicationCommand command) {
        return new BowlingGameResultProcessor(
            command.getInputFile(),
            new BowlingResultFileReader(),
            createResultOutputWriter(command.getOutputType()),
            createPlayerActionParser(),
            createGame());
    }

    private static ResultOutputWriter createResultOutputWriter(OutputType outputType) {
        if (outputType.equals(OutputType.CONSOLE)) {
            return new BowlingResultConsoleOutputWriter();
        }
        return new BowlingResultFileOutputWriter(new File(DEFAULT_OUTPUT_FILE));
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
