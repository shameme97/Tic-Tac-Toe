package com.example.TicTacToe.Service;


import java.util.List;

public interface GameService {
    public String submitMove(int size, List<String> moveSet, boolean inProgress);
    public void resetStats();
    public int[] showResults();
    public String[] getWinningMoves();
    public List<String[]> getGameDetails();
    public String getLastValueOfFirstTurn();
    public double[] getWinStatsTurn();
    public double[] getWinStats();
    public void setPlayerNames(String player1Name, String player2Name);
    public String getCrossName();
    public String getCircleName();
    public int getNumberOfMovesMade();
}
