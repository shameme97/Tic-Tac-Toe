package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Player;
import com.example.TicTacToe.Repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    public PlayerRepository playerRepository;

    @Override
    public boolean addMove(Player player, int[] move) {
        player.moves.add(move);
        playerRepository.save(player);
        // check if won
        return false;
    }

    @Override
    public void resetGame() {
        // reset moves of both players
    }
}
