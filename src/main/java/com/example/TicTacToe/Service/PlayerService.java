package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Player;

public interface PlayerService {
    public boolean addMove(Player player, int[] move);
    public void resetGame();
}
