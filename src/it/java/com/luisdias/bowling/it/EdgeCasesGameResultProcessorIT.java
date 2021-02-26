package com.luisdias.bowling.it;

import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class EdgeCasesGameResultProcessorIT extends AbstractConsoleBasedIT {

    private static Stream<Arguments> execute() {
        return Stream.of(
            Arguments.of("edge-cases/bad-input-1.txt", "edge-cases/default-error-output.txt"),
            Arguments.of("edge-cases/bad-input-2.txt", "edge-cases/default-error-output.txt"),
            Arguments.of("edge-cases/more-throws.txt", "edge-cases/more-throws-output.txt"),
            Arguments.of("edge-cases/negative-input.txt", "edge-cases/default-error-output.txt")
        );
    }

    @ParameterizedTest
    @MethodSource
    void execute(String input, String output) throws IOException {
        verifyError(input, output);
    }
}
