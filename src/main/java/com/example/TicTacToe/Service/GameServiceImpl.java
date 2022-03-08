package com.example.TicTacToe.Service;

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
}
