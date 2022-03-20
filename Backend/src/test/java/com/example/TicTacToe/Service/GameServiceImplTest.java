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
import static org.mockito.Mockito.when;

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
        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"0 0", "0 1", "0 2"});
        winStates.add(new String[]{"1 0", "1 1", "1 2"});
        winStates.add(new String[]{"2 0", "2 1", "2 2"});
        winStates.add(new String[]{"0 0", "1 0", "2 0"});
        winStates.add(new String[]{"0 1", "1 1", "2 1"});
        winStates.add(new String[]{"0 2", "1 2", "2 2"});
        winStates.add(new String[]{"0 0", "1 1", "2 2"});
        winStates.add(new String[]{"0 2", "1 1", "2 0"});

        Board board = new Board(1, 3, winStates, "", "");
        when(gameRepository.insert(board)).thenReturn(board);
        Board result = gameService.createBoard(3);
        List<String[]> boardWinStates = result.getWinStates();
        int found = 0;
        for (String[] winState: winStates){
            for (String[] boardWinState: boardWinStates){
                if (Arrays.toString(winState).equals(Arrays.toString(boardWinState)))
                    ++found;
            }
        }
        assertEquals(8, found);
        assertEquals(3, result.getSize());
    }

    @Test
    void submitMove() {
        List<String> moveSet = Arrays.asList("CIRCLE 2 0","CROSS 0 0","CIRCLE 2 1","CROSS 1 1","CIRCLE 2 2");
        assertEquals("CIRCLE Wins!", gameService.submitMove(3, moveSet, true));

        List<String> moveSet2 = Arrays.asList("CROSS 2 0","CIRCLE 0 2","CROSS 2 1","CIRCLE 2 1","CROSS 2 2");
        assertEquals("CROSS Wins!", gameService.submitMove(4, moveSet2, true));

        List<String> moveSet3 = Arrays.asList("CROSS 2 0","CIRCLE 0 2","CROSS 2 1","CIRCLE 2 1");
        assertEquals("Match In Progress!", gameService.submitMove(5, moveSet3, true));

        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"0 0", "0 1", "0 2"});
        winStates.add(new String[]{"1 0", "1 1", "1 2"});
        winStates.add(new String[]{"2 0", "2 1", "2 2"});
        winStates.add(new String[]{"0 0", "1 0", "2 0"});
        winStates.add(new String[]{"0 1", "1 1", "2 1"});
        winStates.add(new String[]{"0 2", "1 2", "2 2"});
        winStates.add(new String[]{"0 0", "1 1", "2 2"});
        winStates.add(new String[]{"0 2", "1 1", "2 0"});

        Board board = new Board(1, 3, winStates,"", "");
        List<String> moveSet4 = Arrays.asList("CIRCLE 2 0","CROSS 0 0","CIRCLE 2 1","CROSS 1 1","CIRCLE 2 2");
        List<Board> allBoards = new ArrayList<>();
        allBoards.add(board);

        when(gameRepository.findAll()).thenReturn(allBoards);
        assertEquals("CIRCLE Wins!", gameService.submitMove(3, moveSet4, false));
    }

    @Test
    void checkForWinner() {
        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"0 0", "0 1", "0 2"});
        winStates.add(new String[]{"1 0", "1 1", "1 2"});
        winStates.add(new String[]{"2 0", "2 1", "2 2"});
        winStates.add(new String[]{"0 0", "1 0", "2 0"});
        winStates.add(new String[]{"0 1", "1 1", "2 1"});
        winStates.add(new String[]{"0 2", "1 2", "2 2"});
        winStates.add(new String[]{"0 0", "1 1", "2 2"});
        winStates.add(new String[]{"0 2", "1 1", "2 0"});

        Board board = new Board(1, 3, winStates,"", "");
        List<String> moveSet = Arrays.asList("CROSS 2 0","CIRCLE 0 0","CROSS 2 1","CIRCLE 1 1","CROSS 2 2");
        assertEquals("CROSS Wins!", gameService.checkForWinner(board, moveSet));

        List<String> moveSet2 = Arrays.asList("CIRCLE 0 0" ,"CROSS 0 2","CIRCLE 0 1","CROSS 1 0",
                "CIRCLE 1 2","CROSS 1 1", "CIRCLE 2 0" ,"CROSS 2 2","CIRCLE 2 1");
        assertEquals("Draw", gameService.checkForWinner(board, moveSet2));
    }

    @Test
    void foundWinner() {
        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"X", "0 1", "0 2"});
        winStates.add(new String[]{"1 0", "X", "1 2"});
        winStates.add(new String[]{"O", "O", "O"});
        winStates.add(new String[]{"X", "1 0", "O"});
        winStates.add(new String[]{"0 1", "X", "O"});
        winStates.add(new String[]{"0 2", "1 2", "O"});
        winStates.add(new String[]{"X", "X", "O"});
        winStates.add(new String[]{"0 2", "X", "O"});

        Board board = new Board(1, 3, winStates,"", "");
        when(gameRepository.save(board)).thenReturn(board);

        String[] movesMade1 = {"O", "O", "O"};
        String[] movesMade2 = {"X", "X", "O"};
        assertTrue(gameService.foundWinner(board, movesMade1));
        assertFalse(gameService.foundWinner(board, movesMade2));
    }

    @Test
    void setWinningMoves() {
        List<String[]> copyOfWinStates = new ArrayList<>();
        copyOfWinStates.add(new String[]{"0 0", "0 1", "0 2"});
        copyOfWinStates.add(new String[]{"1 0", "1 1", "1 2"});
        copyOfWinStates.add(new String[]{"2 0", "2 1", "2 2"});
        copyOfWinStates.add(new String[]{"0 0", "1 0", "2 0"});
        copyOfWinStates.add(new String[]{"0 1", "1 1", "2 1"});
        copyOfWinStates.add(new String[]{"0 2", "1 2", "2 2"});
        copyOfWinStates.add(new String[]{"0 0", "1 1", "2 2"});
        copyOfWinStates.add(new String[]{"0 2", "1 1", "2 0"});

        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"O", "0 1", "0 2"});
        winStates.add(new String[]{"1 0", "O", "1 2"});
        winStates.add(new String[]{"X", "X", "X"});
        winStates.add(new String[]{"O", "1 0", "X"});
        winStates.add(new String[]{"0 1", "O", "X"});
        winStates.add(new String[]{"0 2", "1 2", "X"});
        winStates.add(new String[]{"O", "O", "X"});
        winStates.add(new String[]{"0 2", "O", "X"});

        Board board = new Board(1, 3, winStates,"CROSS", "CROSS", new String[3],5, "", "");
        when(gameRepository.save(board)).thenReturn(board);
        gameService.setWinningMoves(board, copyOfWinStates);

        String[] expectedWinningMove = {"2 0", "2 1", "2 2"};
        assertArrayEquals(expectedWinningMove, board.getWinningMove());
    }

    @Test
    void checkForDraw() {
        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"O", "X", "O"});
        winStates.add(new String[]{"X", "O", "X"});
        winStates.add(new String[]{"X", "O", "X"});
        winStates.add(new String[]{"O", "X", "X"});
        winStates.add(new String[]{"X", "O", "O"});
        winStates.add(new String[]{"O", "X", "X"});
        winStates.add(new String[]{"O", "O", "X"});
        winStates.add(new String[]{"O", "O", "X"});

        Board board = new Board(1, 3, winStates,"", "");
        when(gameRepository.save(board)).thenReturn(board);

        gameService.checkForDraw(board, 9);
        assertEquals("Draw", board.getWinner());

        assertEquals("Match In Progress!", gameService.checkForDraw(board, 7));
    }

    @Test
    void showResults() {
        List<String[]> winStates = new ArrayList<>();
        winStates.add(new String[]{"O", "0 1", "0 2"});
        winStates.add(new String[]{"1 0", "O", "1 2"});
        winStates.add(new String[]{"X", "X", "X"});
        winStates.add(new String[]{"O", "1 0", "X"});
        winStates.add(new String[]{"0 1", "O", "X"});
        winStates.add(new String[]{"0 2", "1 2", "X"});
        winStates.add(new String[]{"O", "O", "X"});
        winStates.add(new String[]{"0 2", "O", "X"});

        Board board = new Board(1, 3, winStates,"CROSS", "CROSS", new String[3],7, "", "");
        gameRepository.insert(board);
        Board board2 = new Board(2, 3, winStates,"CIRCLE", "Draw", new String[3],9, "", "");
        gameRepository.insert(board2);
        List<Board> allBoards = new ArrayList<>();
        allBoards.add(board);
        allBoards.add(board2);

        when(gameRepository.findAll()).thenReturn(allBoards);
        assertArrayEquals(new int[]{1, 0, 1}, gameService.showResults());
    }

}