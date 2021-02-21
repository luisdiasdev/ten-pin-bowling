package com.luisdias.bowling.impl;

import com.luisdias.bowling.PlayerActionGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingPlayerActionGroupFactoryTest {

    BowlingPlayerActionGroupFactory factory;

    @BeforeEach
    void setUp() {
        factory = new BowlingPlayerActionGroupFactory();
    }

    @Test
    void shouldReturnInstanceOfBowlingPlayerActionGroup() {
        PlayerActionGroup playerActionGroup = factory.createPlayerActionGroup();

        assertThat(playerActionGroup)
            .isInstanceOf(BowlingPlayerActionGroup.class);
    }
}