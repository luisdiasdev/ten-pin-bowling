package com.luisdias.bowling;

public enum ParsingResultError {
    BLANK_LINE("The current line is null or blank."),
    WRONG_NUMBER_OF_ARGUMENTS("The current line does not have two space separated strings"),
    IS_NOT_A_VALID_NUMBER("The current action value is not a number or is not in the accepted range (0-10)");

    private final String message;

    ParsingResultError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
