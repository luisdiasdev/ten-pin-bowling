package com.luisdias.bowling.impl;

import com.luisdias.bowling.ResultOutputWriter;
import java.util.List;
import java.util.Objects;

public class BowlingResultConsoleOutputWriter implements ResultOutputWriter {

    @Override
    public void write(List<String> result) {
        if (Objects.isNull(result) || result.isEmpty()) {
            return;
        }
        result.forEach(System.out::print);
    }
}
