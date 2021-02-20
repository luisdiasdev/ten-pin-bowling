package com.luisdias.bowling.impl;

import com.luisdias.bowling.*;
import io.vavr.control.Either;
import org.apache.commons.lang3.StringUtils;

public class BowlingPlayerActionParser implements PlayerActionParser {

    private final PlayerActionFactory playerActionFactory;

    public BowlingPlayerActionParser(PlayerActionFactory playerActionFactory) {
        this.playerActionFactory = playerActionFactory;
    }

    @Override
    public Either<ParsingResultError, PlayerAction> parse(String line) {
        if (StringUtils.isBlank(line)) {
            return Either.left(ParsingResultError.BLANK_LINE);
        }
        String[] parts = line.split(" ");
        if (parts.length != 2) {
            return Either.left(ParsingResultError.WRONG_NUMBER_OF_ARGUMENTS);
        }
        String playerName = parts[0];
        Either<ParsingResultError, ActionValue> actionParseResult = parseAction(parts[1]);
        if (actionParseResult.isLeft()) {
            return Either.left(actionParseResult.getLeft());
        }
        return actionParseResult
            .filterOrElse(this::isBetweenZeroAndTen, action -> ParsingResultError.IS_NOT_A_VALID_NUMBER)
            .map(action -> playerActionFactory.createPlayerAction(playerName, action));
    }

    private boolean isBetweenZeroAndTen(ActionValue action) {
        Integer value = action.getValue();
        return value >= 0 && value <= 10;
    }

    private Either<ParsingResultError, ActionValue> parseAction(String value) {
        if (isFoul(value)) {
            return Either.right(new ActionValue(0, true));
        }
        try {
            Integer intValue = Integer.valueOf(value);
            return Either.right(new ActionValue(intValue, false));
        } catch (NumberFormatException ex) {
            return Either.left(ParsingResultError.IS_NOT_A_VALID_NUMBER);
        }
    }

    private boolean isFoul(String value) {
        return value.equals("F");
    }
}
