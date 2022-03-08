package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Player;

import java.util.ArrayList;
import java.util.List;

public interface PlayerService {
    public boolean addMove(ArrayList<String> moveSet);
    public void clearBoard();
    public void resetStats();
    public void increaseWin(Player player);

}
