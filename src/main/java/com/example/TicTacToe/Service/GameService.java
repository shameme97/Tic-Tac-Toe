package com.example.TicTacToe.Service;


import com.example.TicTacToe.Model.Board;

import java.util.ArrayList;

public interface GameService {
    public void beginGame(int size);
    public String addMove(Board board, ArrayList<String> moveSet);
    public void clearBoard();
    public void resetStats();

}
