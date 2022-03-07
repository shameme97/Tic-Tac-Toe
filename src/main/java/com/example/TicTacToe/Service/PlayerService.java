package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Player;

public interface PlayerService {
    public boolean addMove(Player player, int[] move);
    public void clearBoard();
    public void resetStats();
    public void increaseWin(Player player);

}
