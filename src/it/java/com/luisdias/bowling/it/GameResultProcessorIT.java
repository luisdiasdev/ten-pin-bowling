package com.luisdias.bowling.it;

import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GameResultProcessorIT extends AbstractConsoleBasedIT {

    private static Stream<Arguments> run() {
        return Stream.of(
            Arguments.of("jeff-perfect.txt", "jeff-perfect-output.txt"),
            Arguments.of("jeff-zeroes.txt", "jeff-zeroes-output.txt"),
            Arguments.of("jeff-fouls.txt", "jeff-fouls-output.txt"),
            Arguments.of("sample-input.txt", "sample-input-output.txt")
        );
    }

    @ParameterizedTest
    @MethodSource
    void run(String inputFileName, String outputFileName) throws IOException {
        verify(inputFileName, outputFileName);
    }
}
