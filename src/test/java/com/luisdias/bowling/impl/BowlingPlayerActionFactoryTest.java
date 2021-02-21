package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingPlayerActionFactoryTest {

    BowlingPlayerActionFactory factory;

    @BeforeEach
    void setUp() {
        factory = new BowlingPlayerActionFactory();
    }

    @Test
    void shouldReturnAnInstanceOfBowlingPlayerAction() {
        ActionValue actionValue = new ActionValue(10, false);
        String playerName = "Rob";

        PlayerAction playerAction = factory.createPlayerAction(playerName, actionValue);

        assertThat(playerAction)
            .isInstanceOf(BowlingPlayerAction.class)
            .extracting(PlayerAction::getPlayerName, PlayerAction::getValue)
            .containsExactly(playerName, actionValue);
    }
}