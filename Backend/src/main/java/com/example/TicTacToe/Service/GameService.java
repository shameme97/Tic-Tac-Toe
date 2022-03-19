package com.example.TicTacToe.Service;


import java.util.List;

public interface GameService {
    public String submitMove(int size, List<String> moveSet, boolean inProgress);
    public void resetStats();
    public int[] showResults();
    public String[] getWinningMoves();
    public List<String[]> getGameDetails();
    public String getLastValueOfFirstTurn();
    public double[] getWinStats();
    public void setCrossName(String newName);
    public void setCircleName(String newName);
    public String getCrossName();
    public String getCircleName();
}
