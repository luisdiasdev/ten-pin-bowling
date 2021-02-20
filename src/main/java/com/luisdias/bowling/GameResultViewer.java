package com.luisdias.bowling;

import java.io.File;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "result-viewer")
public class GameResultViewer implements Callable<Integer> {

    @Parameters(index = "0", description = "The file containing the results of the bowling game")
    private File file;

    private final GameResultFileReader gameResultFileReader;
    private final Game game;

    public GameResultViewer(GameResultFileReader gameResultFileReader, Game game) {
        this.gameResultFileReader = gameResultFileReader;
        this.game = game;
    }

    @Override
    public Integer call() {
        System.out.println("The file is: " + file.getAbsolutePath());
        gameResultFileReader.read(file);
        return 0;
    }
}
