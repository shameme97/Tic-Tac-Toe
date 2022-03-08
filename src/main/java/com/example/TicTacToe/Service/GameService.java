package com.example.TicTacToe.Service;


import java.util.ArrayList;

public interface GameService {
    public void beginGame(int size);
    public String addMove(ArrayList<String> moveSet);
    public void clearBoard();
    public void resetStats();

}
