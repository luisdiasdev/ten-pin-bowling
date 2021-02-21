package com.luisdias.bowling.impl;

import com.luisdias.bowling.ResultOutputWriter;
import java.util.List;
import java.util.Objects;

public class BowlingResultConsoleOutputWriter implements ResultOutputWriter {

    @Override
    public boolean write(List<String> result) {
        if (Objects.isNull(result) || result.isEmpty()) {
            System.out.println("No results to print =(");
            return false;
        }
        result.forEach(System.out::println);
        return true;
    }
}
