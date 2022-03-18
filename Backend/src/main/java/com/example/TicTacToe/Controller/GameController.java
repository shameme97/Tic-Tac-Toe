package com.example.TicTacToe.Controller;

import com.example.TicTacToe.Service.GameService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
//@CrossOrigin(origins = "https://tictactoe-shameme.herokuapp.com/")
@Api(value = "Game Details")
@RestController
@AllArgsConstructor
public class GameController {

    @Autowired
    public GameService gameService;

    @PostMapping(value = "/{size}/submitMoves/{inProgress}")
    public String submitMove(@PathVariable("size") int size,@PathVariable("inProgress") boolean inProgress, @RequestBody List<String> moveSet){
        return gameService.submitMove(size, moveSet, inProgress);
    }

    @PostMapping(value = "/reset")
    public void resetStats(){
        gameService.resetStats();
    }

    @GetMapping(value = "/results")
    public int[] showResults(){
        return gameService.showResults();
    }

    @GetMapping(value = "/winningMoves")
    public String[] getWinningMoves(){
        return gameService.getWinningMoves();
    }

    @GetMapping(value = "/gameDetails")
    public List<String[]> getGameDetails(){
        return gameService.getGameDetails();
    }

    @GetMapping(value = "/lastFirstTurn")
    public String getLastValueOfFirstTurn(){
        return gameService.getLastValueOfFirstTurn();
    }
}
