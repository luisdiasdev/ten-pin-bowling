package com.luisdias.bowling;

import io.vavr.control.Either;

public interface PlayerActionParser {

    Either<ParsingResultError, PlayerAction> parse(String line);
}
