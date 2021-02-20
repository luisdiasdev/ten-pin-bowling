package com.luisdias.bowling.impl;

import com.luisdias.bowling.Game;
import com.luisdias.bowling.GameResultFileReader;
import com.luisdias.bowling.GameResultViewer;
import com.luisdias.bowling.PlayerActionParser;
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
        return 0;
    }

    private boolean processLine(String line) {
//        PlayerAction playerAction = playerActionParser.parse(line);
//        game.doPlayerAction(playerAction);
//        game.checkStatus();
        return true;
    }
}
