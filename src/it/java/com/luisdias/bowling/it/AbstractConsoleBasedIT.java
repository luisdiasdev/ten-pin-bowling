package com.luisdias.bowling.it;

import com.luisdias.bowling.GameResultProcessor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.luisdias.bowling.it.TestFileHelper.getFilePathFromResources;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractConsoleBasedIT {

    protected PrintStream originalOut = System.out;
    protected ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private Bootstrap bootstrap;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outStream));
        bootstrap = new Bootstrap();
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(originalOut);
        outStream.close();
    }

    protected void verify(String inputFileName, String expectedOutputFileName) throws IOException {
        File inputFile = new File(getFilePathFromResources(inputFileName));
        GameResultProcessor processor = bootstrap.createProcessor(inputFile);

        Integer result = processor.process();

        byte[] expectedBytes = Files.readAllBytes(Paths.get(getFilePathFromResources(expectedOutputFileName)));
        assertEquals(0, result);
        try {
            assertArrayEquals(outStream.toByteArray(), expectedBytes);
        } catch (Throwable ex) {
            originalOut.println("Expected output:");
            originalOut.println(outStream.toString());
        }
    }
}
