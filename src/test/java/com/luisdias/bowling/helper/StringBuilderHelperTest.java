package com.luisdias.bowling.helper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StringBuilderHelperTest {

    @Test
    void shouldThrowNullPointerIfInputStringBuilderIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> StringBuilderHelper.appendIfFalse(null, "something", false));
    }

    @Test
    void shouldThrowNullPointerIfValueIsNull() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> StringBuilderHelper.appendIfFalse(new StringBuilder(), null, false));
    }

    @Test
    void shouldAppendToTheStringBuilderIfConditionIsFalse() {
        StringBuilder input = new StringBuilder();
        String content = "\n\n\t\tHello\t\tWorld";

        StringBuilderHelper.appendIfFalse(input, content, false);

        assertThat(input.toString())
            .isEqualTo(content);
    }

    @Test
    void shouldNotAppendToTheStringBuilderIfConditionIsTrue() {
        StringBuilder input = new StringBuilder();
        String content = "\n\n\t\tHello\t\tWorld";

        StringBuilderHelper.appendIfFalse(input, content, true);

        assertThat(input.toString()).isEmpty();
    }
}