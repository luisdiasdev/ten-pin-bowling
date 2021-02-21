package com.luisdias.bowling.impl;

import com.luisdias.bowling.ActionValue;
import com.luisdias.bowling.PlayerActionGroup;
import com.luisdias.bowling.PlayerActionGroupFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BowlingPlayerTest {

    String playerName = "Nate";
    BowlingPlayer player;
    PlayerActionGroupFactory playerActionGroupFactory;

    @BeforeEach
    void setUp() {
        playerActionGroupFactory = mock(PlayerActionGroupFactory.class);
    }

    void createPlayer() {
        player = new BowlingPlayer(playerName, playerActionGroupFactory);
    }

    PlayerActionGroup mockActionGroup() {
        return mock(PlayerActionGroup.class);
    }

    PlayerActionGroup mockCompleteActionGroup() {
        PlayerActionGroup actionGroup = mockActionGroup();
        when(actionGroup.isComplete()).thenReturn(true);
        return actionGroup;
    }

    @Test
    void shouldReturnPlayerName() {
        createPlayer();
        assertThat(player.getName()).isEqualTo(playerName);
    }

    @DisplayName("doAction")
    @Nested
    class DoAction {

        ActionValue strikeAction = new ActionValue(10, false);

        @Test
        void shouldReturnFalseIfRollCountIsTooHigh() {
            PlayerActionGroup actionGroup = mockCompleteActionGroup();
            when(playerActionGroupFactory.createPlayerActionGroup())
                .thenReturn(actionGroup);
            createPlayer();

            int maxRolls = 21;
            int i = 0;
            while (i++ < maxRolls) {
                player.doAction(strikeAction);
            }

            boolean result = player.doAction(strikeAction);

            assertThat(result).isFalse();
        }

        @Test
        void shouldAddActionIfCurrentGroupIsNotComplete() {
            PlayerActionGroup actionGroup = mockActionGroup();
            when(playerActionGroupFactory.createPlayerActionGroup())
                .thenReturn(actionGroup);
            createPlayer();

            player.doAction(strikeAction);

            verify(actionGroup, times(1)).addAction(strikeAction);
        }

        @Test
        void shouldCreateNextGroupIfCurrentOneIsComplete() {
            PlayerActionGroup currentActionGroup = mockCompleteActionGroup();
            PlayerActionGroup nextActionGroup = mockActionGroup();
            when(playerActionGroupFactory.createPlayerActionGroup())
                .thenReturn(currentActionGroup)
                .thenReturn(nextActionGroup);

            createPlayer();

            player.doAction(strikeAction);

            assertThat(player.getCompletedActionGroups().get(0))
                .isNotEqualTo(nextActionGroup)
                .isEqualTo(currentActionGroup);
        }

        @Test
        void shouldCreateNextGroupAndSetLastGroupIfIsLastFrame() {
            PlayerActionGroup lastActionGroup = mockActionGroup();
            int maxFrames = 10;
            AtomicInteger currentCallCount = new AtomicInteger(maxFrames);
            when(playerActionGroupFactory.createPlayerActionGroup())
                .thenAnswer(invocation -> {
                    if (currentCallCount.getAndDecrement() > 1) {
                        return mockCompleteActionGroup();
                    }
                    return lastActionGroup;
                });
            createPlayer();

            int i = 0;
            while (i++ < maxFrames) {
                player.doAction(strikeAction);
            }

            verify(lastActionGroup, times(1)).setAsLastGroup();
        }
    }

    @Test
    void shouldReturnListOfCompletedFrames() {

    }
}