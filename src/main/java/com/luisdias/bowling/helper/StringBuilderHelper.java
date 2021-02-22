package com.luisdias.bowling.helper;

import java.util.Objects;

public class StringBuilderHelper {

    private StringBuilderHelper() {
        throw new IllegalStateException("utility class constructor should not be called");
    }

    public static void appendIfFalse(StringBuilder sb, String value, boolean isLast) {
        Objects.requireNonNull(sb, "The input string builder must not be null");
        Objects.requireNonNull(value, "The value must not be null");
        if (!isLast) {
            sb.append(value);
        }
    }
}
