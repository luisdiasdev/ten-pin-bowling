package com.luisdias.bowling.impl;

import com.luisdias.bowling.ResultOutputWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BowlingResultFileOutputWriter implements ResultOutputWriter {

    private final Logger logger = LoggerFactory.getLogger(BowlingResultFileOutputWriter.class);
    private final File file;

    public BowlingResultFileOutputWriter(File file) {
        this.file = file;
    }

    @Override
    public void write(List<String> result) {
        if (Objects.isNull(result) || result.isEmpty()) {
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : result) {
                bw.write(line);
            }
        } catch (IOException ex) {
            logger.error("Error while writing to the file {}", file.getAbsoluteFile(), ex);
        }
    }
}
