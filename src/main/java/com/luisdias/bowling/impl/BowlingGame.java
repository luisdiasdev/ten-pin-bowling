package com.luisdias.bowling.impl;

import com.luisdias.bowling.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BowlingGame implements Game {

    private final PlayerFactory playerFactory;
    private final ResultHeaderProvider resultHeaderProvider;
    private final ScoreCalculator scoreCalculator;
    private final ScoreConverter scoreConverter;
    private final List<Player> players;

    public BowlingGame(
        PlayerFactory playerFactory,
        ResultHeaderProvider resultHeaderProvider,
        ScoreCalculator scoreCalculator,
        ScoreConverter scoreConverter) {
        this.playerFactory = playerFactory;
        this.resultHeaderProvider = resultHeaderProvider;
        this.scoreCalculator = scoreCalculator;
        this.scoreConverter = scoreConverter;
        this.players = new ArrayList<>();
    }

    @Override
    public boolean doPlayerAction(PlayerAction action) {
        Player currentPlayer = players.stream()
            .filter(player -> action.getPlayerName().equals(player.getName()))
            .findFirst()
            .orElseGet(() -> addPlayer(action.getPlayerName()));
        return currentPlayer.doAction(action.getValue());
    }

    @Override
    public List<String> generateResults() {
        if (!hasPlayers()) {
            return Collections.emptyList();
        }
        return Stream.concat(
            resultHeaderProvider.getHeader().stream(),
            players.stream().flatMap(this::generateEachPlayerResult))
            .collect(Collectors.toList());
    }

    private boolean hasPlayers() {
        return !players.isEmpty();
    }

    private Player addPlayer(String playerName) {
        Player bowlingPlayer = playerFactory.createPlayer(playerName);
        players.add(bowlingPlayer);
        return bowlingPlayer;
    }

    private Stream<? extends String> generateEachPlayerResult(Player player) {
        List<PlayerActionGroup> completedActionGroups = player.getCompletedActionGroups();
        List<Score> scores = scoreCalculator.calculate(completedActionGroups);
        return scoreConverter.convert(player.getName(), scores).stream();
    }
}
