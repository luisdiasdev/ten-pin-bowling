package com.luisdias.bowling.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.*;

class BowlingResultFileOutputWriterTest {

    @TempDir Path tempDir;
    String outputFileName = "output.txt";
    BowlingResultFileOutputWriter writer;

    @Test
    void shouldNotWriteFileIfInputIsNull() {
        Path outputFilePath = createWriterAndReturnPath();

        writer.write(null);

        assertThat(Files.exists(outputFilePath)).isFalse();
    }

    @Test
    void shouldWriteEachItemFromTheListAsALineInTheOutputFile() throws IOException {
        String first = "FIRST LINE";
        String second = "SECOND LINE";
        String firstLine = first + "\n";
        String secondLine = second + "\n";

        Path outputFilePath = createWriterAndReturnPath();

        writer.write(Arrays.asList(firstLine, secondLine));

        assertThat(Files.readAllLines(outputFilePath))
            .containsExactly(first, second);
    }

    Path createWriterAndReturnPath() {
        Path outputFilePath = tempDir.resolve(outputFileName);
        writer = new BowlingResultFileOutputWriter(outputFilePath.toFile());
        return outputFilePath;
    }
}
