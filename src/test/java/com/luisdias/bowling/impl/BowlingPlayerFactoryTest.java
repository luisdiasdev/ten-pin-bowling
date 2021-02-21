package com.luisdias.bowling.impl;

import com.luisdias.bowling.Player;
import com.luisdias.bowling.PlayerActionGroupFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BowlingPlayerFactoryTest {

    PlayerActionGroupFactory playerActionGroupFactory;
    BowlingPlayerFactory factory;

    @BeforeEach
    void setUp() {
        playerActionGroupFactory = mock(PlayerActionGroupFactory.class);
        factory = new BowlingPlayerFactory(playerActionGroupFactory);
    }

    @Test
    void shouldCreateAnInstanceOfBowlingPlayer() {
        String playerName = "Joe";

        Player player = factory.createPlayer(playerName);

        assertThat(player)
            .isInstanceOf(BowlingPlayer.class)
            .extracting(Player::getName)
            .isEqualTo(playerName);
    }
}