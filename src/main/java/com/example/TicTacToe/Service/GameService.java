package com.example.TicTacToe.Service;


import com.example.TicTacToe.Model.Board;

import java.util.ArrayList;
import java.util.List;

public interface GameService {
    public void beginGame(int size);
    public String addMove(List<String> moveSet);
    public void resetStats();
    public int[] showResults();
}
