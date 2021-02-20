package com.luisdias.bowling.impl;

import com.luisdias.bowling.Game;
import com.luisdias.bowling.Player;
import com.luisdias.bowling.PlayerAction;
import com.luisdias.bowling.PlayerFactory;
import java.util.ArrayList;
import java.util.List;

public class BowlingGame implements Game {

    private final PlayerFactory playerFactory;
    private final List<Player> players;

    public BowlingGame(PlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
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
    public void printResult() {
        players
            .forEach(player -> {
                System.out.println("Player: " + player.getName());
                System.out.println("Score: " + player.getCurrentScore());
                System.out.println();
            });
    }

    private Player addPlayer(String playerName) {
        Player bowlingPlayer = playerFactory.createPlayer(playerName);
        players.add(bowlingPlayer);
        return bowlingPlayer;
    }
}
