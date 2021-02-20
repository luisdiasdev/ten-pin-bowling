package com.luisdias.bowling.impl;

import com.luisdias.bowling.GameResultFileReader;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BowlingResultFileReader implements GameResultFileReader {

    private final Logger logger = LoggerFactory.getLogger(BowlingResultFileReader.class);

    @Override
    public void read(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException ex) {
            logger.error("The file {} was not found.", file, ex);
        } catch (IOException ex) {
            logger.error("Error while reading from file {}", file, ex);
        }
    }
}
