package com.luisdias.bowling.cli;

import com.luisdias.bowling.OutputType;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.function.Function;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "processor")
public class ApplicationCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "The type of output: CONSOLE or FILE")
    private OutputType outputType;

    @Parameters(index = "1", description = "The input file containing the results of the bowling game")
    private File inputFile;

    private final Function<ApplicationCommand, Integer> executor;

    public ApplicationCommand(Function<ApplicationCommand, Integer> executor) {
        this.executor = executor;
    }

    @Override
    public Integer call() {
        return executor.apply(this);
    }

    public OutputType getOutputType() {
        return outputType;
    }

    public File getInputFile() {
        return inputFile;
    }
}
