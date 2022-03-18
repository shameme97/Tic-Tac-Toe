package com.example.TicTacToe.Service;


import java.util.List;

public interface GameService {
    public String submitMove(int size, List<String> moveSet, boolean inProgress);
    public void resetStats();
    public int[] showResults();
    public String[] getWinningMoves();
    public List<String[]> getGameDetails();
    public String getLastValueOfFirstTurn();
}
