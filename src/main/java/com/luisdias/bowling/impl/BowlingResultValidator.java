package com.luisdias.bowling.impl;

import com.luisdias.bowling.ResultValidator;
import java.util.List;
import java.util.Objects;

public class BowlingResultValidator implements ResultValidator {

    @Override
    public boolean isValid(List<String> results) {
        return Objects.nonNull(results) && results.size() > 0;
    }
}
