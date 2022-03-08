package com.example.TicTacToe.Controller;

import com.example.TicTacToe.Service.PlayerService;
import com.example.TicTacToe.Model.Player;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class PlayerController {

    @Autowired
    public PlayerService playerService;

    @PostMapping(value = "/{id}")
    public boolean addMove(@RequestBody ArrayList<String> moveSet){
        return playerService.addMove(moveSet);
    }

    @PostMapping(value = "/clear")
    public void clearBoard(){
        playerService.clearBoard();
    }

    @PostMapping(value = "/reset")
    public void resetStats(){
        playerService.resetStats();
    }

    @PostMapping(value = "/win/{id}")
    public void increaseWin(@PathVariable("id") Player player){
        playerService.increaseWin(player);
    }
}
