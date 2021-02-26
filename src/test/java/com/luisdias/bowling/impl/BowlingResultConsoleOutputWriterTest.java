package com.luisdias.bowling.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingResultConsoleOutputWriterTest {

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    BowlingResultConsoleOutputWriter outputWriter;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outStream));
        outputWriter = new BowlingResultConsoleOutputWriter();
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(originalOut);
        outStream.close();
    }

    @Test
    void shouldPrintLinesToConsole() {
        List<String> lines = Stream.of("First line\n", "Second line\n", "Third line\n")
            .collect(Collectors.toList());

        outputWriter.write(lines);

        assertThat(outStream).hasToString(String.join("", lines));
    }

    @Test
    void shouldNotPrintIfInputIsEmpty() {
        outputWriter.write(Collections.emptyList());

        assertThat(outStream).hasToString("");
    }

    @Test
    void shouldNotPrintIfInputIsNull() {
        outputWriter.write(null);

        assertThat(outStream).hasToString("");
    }
}