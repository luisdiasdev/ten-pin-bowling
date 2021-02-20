package com.luisdias.bowling.impl;

import com.luisdias.bowling.GameResultFileReader;
import java.io.*;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BowlingResultFileReader implements GameResultFileReader {

    private final Logger logger = LoggerFactory.getLogger(BowlingResultFileReader.class);

    @Override
    public void read(File file, Predicate<String> linePredicate) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                boolean shouldContinue = linePredicate.test(line);
                if (!shouldContinue) {
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            logger.error("The file {} was not found.", file, ex);
        } catch (IOException ex) {
            logger.error("Error while reading from file {}", file, ex);
        }
    }
}
