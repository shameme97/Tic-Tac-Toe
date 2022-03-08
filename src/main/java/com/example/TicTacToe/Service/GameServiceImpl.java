package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Board;
import com.example.TicTacToe.Repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    @Autowired
    public GameRepository gameRepository;

    @Override
    public void beginGame(int size) {
        // create board object with size win_states
        List<String[]> winStates_horizontal = new ArrayList<>();
        List<String[]> winStates_vertical = new ArrayList<>();
        List<String[]> winStates_rightDiagonal = new ArrayList<>();
        List<String[]> winStates_leftDiagonal = new ArrayList<>();

        setWinStates(size, winStates_horizontal, winStates_vertical);
        setWinStatesDiagonal(size, winStates_rightDiagonal, winStates_leftDiagonal);
    }



    @Override
    public String addMove(ArrayList<String>  moveSet) {
        // check if won
        return "";
    }

    public String gameResults(List<int[]> player1moves, List<int[]> player2moves){

        return null;
    }

    @Override
    public void clearBoard() {
        // reset moves of both players
    }

    @Override
    public void resetStats() {
        // reset wins
    }

    public void increaseWin(String player) {
        // increment win of player by 1
    }

    public void setWinStates(int size, List<String[]> winStates_horizontal, List<String[]> winStates_vertical){
        String[] populate;
        String[] populate2;
        int row, col;
        for (int i=0; i<size; ++i){
            populate = new String[size];
            populate2 = new String[size];
            row = 0;
            col = i;
            for (int j=0; j<size; ++j){
                populate[j] = row+""+col;
                populate2[j] = col+""+row;
                ++row;
            }
            winStates_horizontal.add(populate);
            winStates_vertical.add(populate2);
        }
    }

    public void setWinStatesDiagonal(int size, List<String[]> winStates_rightDiagonal, List<String[]> winStates_leftDiagonal){
        String[] populate;
        String[] populate2;
        int row, col;
        int len = (size-3)*2 + 1;
        for (int i=0; i<len; ++i){
            populate = new String[len];
            populate2 = new String[len];
            row = 0;
            col = i;
            for (int j=0; j<len; ++j){
                populate[j] = row+""+col;
                populate2[j] = row+""+(len-1-col);
                ++row;
                ++col;
            }
            --len;
            winStates_rightDiagonal.add(populate);
            winStates_leftDiagonal.add(populate2);
        }
        len = (size-3)*2 + 1;
        for (int i=1; i<len; ++i){
            populate = new String[len];
            populate2 = new String[len];
            row = i;
            col = 0;
            for (int j=0; j<len; ++j){
                populate[j] = row+""+col;
                populate2[j] = row+""+(len-1-col);
                ++row;
                ++col;
            }
            --len;
            winStates_rightDiagonal.add(populate);
            winStates_leftDiagonal.add(populate2);
        }
    }
}
