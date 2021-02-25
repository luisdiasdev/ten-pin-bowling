package com.luisdias.bowling.impl;

import com.luisdias.bowling.*;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BowlingGameTest {

    PlayerFactory playerFactory;
    ResultHeaderProvider resultHeaderProvider;
    ScoreCalculator scoreCalculator;
    ScoreConverter scoreConverter;
    BowlingGame game;

    ActionValue actionValue = new ActionValue(10, false);
    String playerName = "Allan";

    @BeforeEach
    void setUp() {
        playerFactory = mock(PlayerFactory.class);
        resultHeaderProvider = mock(ResultHeaderProvider.class);
        scoreCalculator = mock(ScoreCalculator.class);
        scoreConverter = mock(ScoreConverter.class);
        game = new BowlingGame(
            playerFactory,
            resultHeaderProvider,
            scoreCalculator,
            scoreConverter);
    }

    Player createPlayerWithName(String playerName) {
        Player player = mock(Player.class);
        doReturn(playerName).when(player).getName();
        when(playerFactory.createPlayer(playerName)).thenReturn(player);
        return player;
    }

    @Test
    void shouldCreatePlayerIfIsFirstRoll() {
        createPlayerWithName(playerName);

        game.doPlayerAction(new BowlingPlayerAction(playerName, actionValue));

        verify(playerFactory, times(1)).createPlayer(playerName);
    }

    @Test
    void shouldNotCreatePlayerIfAlreadyHasDoneAnAction() {
        createPlayerWithName(playerName);

        game.doPlayerAction(new BowlingPlayerAction(playerName, actionValue));
        game.doPlayerAction(new BowlingPlayerAction(playerName, actionValue));

        verify(playerFactory, times(1)).createPlayer(playerName);
    }

    @Test
    void shouldReturnPlayerDoActionResult() {
        Player player = createPlayerWithName(playerName);
        when(player.doAction(actionValue)).thenReturn(true);

        boolean result = game.doPlayerAction(new BowlingPlayerAction(playerName, actionValue));

        assertThat(result).isTrue();
    }

    @Test
    void shouldGenerateResults() {
        String header = "HEADER";
        String line = "LINE";
        when(resultHeaderProvider.getHeader())
            .thenReturn(Collections.singletonList(header));
        Player player = createPlayerWithName(playerName);
        List<PlayerActionGroup> playerActionGroups = Collections.singletonList(mock(PlayerActionGroup.class));
        when(player.getCompletedActionGroups())
            .thenReturn(playerActionGroups);
        List<Score> scores = Collections.singletonList(mock(Score.class));
        when(scoreCalculator.calculate(playerActionGroups))
            .thenReturn(scores);
        when(scoreConverter.convert(playerName, scores))
            .thenReturn(Collections.singletonList(line));

        game.doPlayerAction(new BowlingPlayerAction(playerName, actionValue));

        List<String> results = game.generateResults();

        assertThat(results)
            .containsExactly(header, line);
    }

    @Test
    void shouldNotGenerateValidResultIfNoPlayers() {
        String header = "HEADER";
        when(resultHeaderProvider.getHeader())
            .thenReturn(Collections.singletonList(header));
        Player player = createPlayerWithName(playerName);
        List<PlayerActionGroup> playerActionGroups = Collections.singletonList(mock(PlayerActionGroup.class));
        when(player.getCompletedActionGroups())
            .thenReturn(playerActionGroups);

        List<String> results = game.generateResults();

        assertThat(results).isEmpty();
    }
}