package com.example.TicTacToe.Controller;

import com.example.TicTacToe.Service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class GameController {

    @Autowired
    public GameService gameService;

    @PostMapping(value = "/begin/{size}")
    public void beginGame(@PathVariable("size") int size){
        gameService.beginGame(size);
    }

    @PostMapping(value = "/result")
    public String addMove(@RequestBody ArrayList<String> moveSet){
        return gameService.addMove(moveSet);
    }

    @PostMapping(value = "/clear")
    public void clearBoard(){
        gameService.clearBoard();
    }

    @PostMapping(value = "/reset")
    public void resetStats(){
        gameService.resetStats();
    }

}
