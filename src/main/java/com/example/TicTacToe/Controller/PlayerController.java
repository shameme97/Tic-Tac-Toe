package com.example.TicTacToe.Controller;

import com.example.TicTacToe.Service.PlayerService;
import com.example.TicTacToe.Model.Player;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PlayerController {

    @Autowired
    public PlayerService playerService;

    @PostMapping(value = "/{id}")
    public boolean addMove(@PathVariable("id") Player player, @RequestBody int[] move){
        return playerService.addMove(player, move);
    }

    @PostMapping(value = "/reset")
    public void resetGame(){
        playerService.resetGame();
    }
}
