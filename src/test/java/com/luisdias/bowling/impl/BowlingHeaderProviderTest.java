package com.luisdias.bowling.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingHeaderProviderTest {

    @Test
    void shouldReturnASingleStringContainingTheHeader() {
        BowlingHeaderProvider headerProvider = new BowlingHeaderProvider();

        assertThat(headerProvider.getHeader())
            .hasSize(1)
            .containsExactly("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n");
    }
}