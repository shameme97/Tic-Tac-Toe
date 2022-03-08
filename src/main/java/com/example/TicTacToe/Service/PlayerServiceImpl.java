package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Player;
import com.example.TicTacToe.Repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    public PlayerRepository playerRepository;

    @Override
    public boolean addMove(ArrayList<String>  moveSet) {

        // check if won
        return false;
    }

    public String gameResults(List<int[]> player1moves, List<int[]> player2moves){
        return null;
    }

    @Override
    public void clearBoard() {
        // reset moves of both players
        Optional<Player> p1 = playerRepository.findById("0");
        Optional<Player> p2 = playerRepository.findById("1");
        Player player1 = p1.orElse(null);
        Player player2 = p2.orElse(null);
        if (player1 != null){
            player1.moves.clear();
            playerRepository.save(player1);
        }
        if (player2 != null){
            player2.moves.clear();
            playerRepository.save(player2);
        }
    }

    @Override
    public void resetStats() {
        // reset wins
        Optional<Player> p1 = playerRepository.findById("0");
        Optional<Player> p2 = playerRepository.findById("1");
        Player player1 = p1.orElse(null);
        Player player2 = p2.orElse(null);
        if (player1 != null){
            player1.setWins(0);
            playerRepository.save(player1);
        }
        if (player2 != null){
            player2.setWins(0);
            playerRepository.save(player2);
        }
    }

    @Override
    public void increaseWin(Player player) {
        // increment win of player by 1
        player.setWins(player.getWins()+1);
        playerRepository.save(player);
    }
}
