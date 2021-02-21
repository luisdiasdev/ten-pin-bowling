package com.luisdias.bowling.impl;

import com.luisdias.bowling.Game;
import com.luisdias.bowling.GameResultFileReader;
import com.luisdias.bowling.PlayerActionParser;
import com.luisdias.bowling.ResultOutputWriter;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BowlingGameResultProcessorTest {

    File inputFile;
    GameResultFileReader gameResultFileReader;
    ResultOutputWriter resultOutputWriter;
    PlayerActionParser playerActionParser;
    Game game;
    BowlingGameResultProcessor processor;

    @BeforeEach
    void setUp() {
        inputFile = mock(File.class);
        gameResultFileReader = mock(GameResultFileReader.class);
        resultOutputWriter = mock(ResultOutputWriter.class);
        playerActionParser = mock(PlayerActionParser.class);
        game = mock(Game.class);
        processor = new BowlingGameResultProcessor(
            inputFile,
            gameResultFileReader,
            resultOutputWriter,
            playerActionParser,
            game);
    }

    @Test
    void shouldReturnOneIfWriteWasNotSuccessful() {
        Integer result = processor.process();

        assertThat(result).isEqualTo(1);
    }

    @Test
    void shouldReturnZeroIfWriteWasSuccessful() {
        when(resultOutputWriter.write(anyList()))
            .thenReturn(true);

        Integer result = processor.process();

        assertThat(result).isEqualTo(0);
    }
}