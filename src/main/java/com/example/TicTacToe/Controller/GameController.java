package com.example.TicTacToe.Controller;

import com.example.TicTacToe.Model.Board;
import com.example.TicTacToe.Service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@AllArgsConstructor
public class GameController {

    @Autowired
    public GameService gameService;

    @PostMapping(value = "/begin/{size}")
    public void beginGame(@PathVariable("size") int size){
        gameService.beginGame(size);
    }

    @PostMapping(value = "/submitMoves")
    public String addMove(@RequestBody List<String> moveSet){
        return gameService.addMove(moveSet);
    }

    @PostMapping(value = "/reset")
    public void resetStats(){
        gameService.resetStats();
    }

    @GetMapping(value = "/results")
    public int[] showResults(){
        return gameService.showResults();
    }


}
