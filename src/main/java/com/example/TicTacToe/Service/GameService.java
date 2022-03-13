package com.example.TicTacToe.Service;


import com.example.TicTacToe.Model.Board;

import java.util.ArrayList;
import java.util.List;

public interface GameService {
    public String submitMove(int size, List<String> moveSet, boolean inProgress);
    public void resetStats();
    public int[] showResults();
}
