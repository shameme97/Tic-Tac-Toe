package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Board;
import com.example.TicTacToe.Repository.GameRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class GameServiceImplTest {

    @MockBean
    private GameRepository gameRepository;

    @Autowired
    private GameServiceImpl gameService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    void createBoard() {
        int size = 3;
        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"0 0", "0 1", "0 2"});
        winStates.add(new String[]{"1 0", "1 1", "1 2"});
        winStates.add(new String[]{"2 0", "2 1", "2 2"});
        winStates.add(new String[]{"0 0", "1 0", "2 0"});
        winStates.add(new String[]{"0 1", "1 1", "2 1"});
        winStates.add(new String[]{"0 2", "1 2", "2 2"});
        winStates.add(new String[]{"0 0", "1 1", "2 2"});
        winStates.add(new String[]{"0 2", "1 1", "2 0"});

        Board board = gameService.createBoard(size);
        List<String[]> boardWinStates = board.getWinStates();
        int found = 0;
        for (String[] winState: winStates){
            for (String[] boardWinState: boardWinStates){
                if (Arrays.toString(winState).equals(Arrays.toString(boardWinState)))
                    ++found;
            }
        }
        assertEquals(8, found);
    }

    @Test
    void submitMove() {
    }

    @Test
    void checkForWinner() {
    }

    @Test
    void foundWinner() {
    }

    @Test
    void setWinningMoves() {
    }

    @Test
    void checkForDraw() {
    }

    @Test
    void resetStats() {
    }

    @Test
    void showResults() {
    }

    @Test
    void getWinningMoves() {
    }

}