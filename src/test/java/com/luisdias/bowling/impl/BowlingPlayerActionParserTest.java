package com.luisdias.bowling.impl;

import com.luisdias.bowling.ParsingResultError;
import com.luisdias.bowling.PlayerAction;
import com.luisdias.bowling.PlayerActionFactory;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BowlingPlayerActionParserTest {

    PlayerActionFactory playerActionFactory;
    BowlingPlayerActionParser bowlingPlayerActionParser;

    @BeforeEach
    void setUp() {
        playerActionFactory = mock(PlayerActionFactory.class);
        bowlingPlayerActionParser = new BowlingPlayerActionParser(playerActionFactory);
    }

    @Test
    void shouldReturnBlankLineErrorIfInputIsBlank() {
        String input = "          ";
        Either<ParsingResultError, PlayerAction> result = bowlingPlayerActionParser.parse(input);

        assertThat(result.getLeft())
            .isEqualTo(ParsingResultError.BLANK_LINE);
    }

    @Test
    void shouldReturnBlankLineErrorIfInputIsNull() {
        Either<ParsingResultError, PlayerAction> result = bowlingPlayerActionParser.parse(null);

        assertThat(result.getLeft())
            .isEqualTo(ParsingResultError.BLANK_LINE);
    }

    @Test
    void shouldReturnWrongNumberOfArgumentsErrorIfInputDoesNotContainTwoSpaceSeparatedStrings() {
        String input = "Kate 2 John 3";
        Either<ParsingResultError, PlayerAction> result = bowlingPlayerActionParser.parse(input);

        assertThat(result.getLeft())
            .isEqualTo(ParsingResultError.WRONG_NUMBER_OF_ARGUMENTS);
    }

    @Test
    void shouldReturnIsNotAValidNumberErrorIfSecondStringIsNotAnInteger() {
        String input = "Kate John";
        Either<ParsingResultError, PlayerAction> result = bowlingPlayerActionParser.parse(input);

        assertThat(result.getLeft())
            .isEqualTo(ParsingResultError.IS_NOT_A_VALID_NUMBER);
    }

    @Test
    void shouldReturnIsNotAValidNumberErrorIfSecondStringIsNegative() {
        String input = "Kate -152";
        Either<ParsingResultError, PlayerAction> result = bowlingPlayerActionParser.parse(input);

        assertThat(result.getLeft())
            .isEqualTo(ParsingResultError.IS_NOT_A_VALID_NUMBER);
    }

    @Test
    void shouldReturnIsNotAValidNumberErrorIfSecondStringIsGreaterThanTen() {
        String input = "Kate 152";
        Either<ParsingResultError, PlayerAction> result = bowlingPlayerActionParser.parse(input);

        assertThat(result.getLeft())
            .isEqualTo(ParsingResultError.IS_NOT_A_VALID_NUMBER);
    }
}