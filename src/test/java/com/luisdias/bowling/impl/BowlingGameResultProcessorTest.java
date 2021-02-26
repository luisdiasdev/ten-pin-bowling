package com.luisdias.bowling.impl;

import com.luisdias.bowling.*;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BowlingGameResultProcessorTest {

    File inputFile;
    GameResultFileReader gameResultFileReader;
    ResultOutputWriter resultOutputWriter;
    ResultValidator resultValidator;
    PlayerActionParser playerActionParser;
    Game game;
    BowlingGameResultProcessor processor;

    @BeforeEach
    void setUp() {
        inputFile = mock(File.class);
        gameResultFileReader = mock(GameResultFileReader.class);
        resultOutputWriter = mock(ResultOutputWriter.class);
        resultValidator = mock(ResultValidator.class);
        playerActionParser = mock(PlayerActionParser.class);
        game = mock(Game.class);
        processor = new BowlingGameResultProcessor(
            inputFile,
            gameResultFileReader,
            resultOutputWriter,
            resultValidator,
            playerActionParser,
            game);
    }

    @Test
    void shouldReturnOneIfGameResultWasInvalid() {
        when(gameResultFileReader.read(any(), any()))
            .thenReturn(true);
        when(resultValidator.isValid(anyList()))
            .thenReturn(false);

        Integer result = processor.process();

        assertThat(result).isEqualTo(1);
    }

    @Test
    void shouldReturnZeroIfGameResultWasValid() {
        when(gameResultFileReader.read(any(), any()))
            .thenReturn(true);
        when(resultValidator.isValid(anyList()))
            .thenReturn(true);

        Integer result = processor.process();

        assertThat(result).isZero();
    }

    @Test
    void shouldReturnOneIfReaderReturnFalse() {
        when(gameResultFileReader.read(any(), any()))
            .thenReturn(false);

        Integer result = processor.process();

        assertThat(result).isEqualTo(1);
    }
}