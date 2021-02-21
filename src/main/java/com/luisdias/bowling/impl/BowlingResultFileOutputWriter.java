package com.luisdias.bowling.impl;

import com.luisdias.bowling.ResultOutputWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BowlingResultFileOutputWriter implements ResultOutputWriter {

    private final Logger logger = LoggerFactory.getLogger(BowlingResultFileOutputWriter.class);
    private final File file;

    public BowlingResultFileOutputWriter(File file) {
        this.file = file;
    }

    @Override
    public boolean write(List<String> result) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : result) {
                bw.write(line);
            }
            return true;
        } catch (IOException ex) {
            logger.error("Error while writing to the file {}", file.getAbsoluteFile(), ex);
            return false;
        }
    }
}
