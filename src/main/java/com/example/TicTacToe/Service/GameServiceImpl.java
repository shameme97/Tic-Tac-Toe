package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Board;
import com.example.TicTacToe.Repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

@Slf4j
@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    @Autowired
    public GameRepository gameRepository;

    private static int newId = 1;

    public Board createBoard(int size) {
        // create new board object with size & win_states
        List<String[]> winStates = new ArrayList<>();
        setWinStates(size, winStates);
        Board board = new Board(newId, size, winStates, "", new String[3]);
        newId++;
        gameRepository.insert(board);
        return board;
    }

    @Override
    public String submitMove(int size, List<String> moveSet, boolean newGame) {
        Board board;
        if (newGame){
            board = createBoard(size);
        }else{
            List<Board> allBoards = gameRepository.findAll();
            board = allBoards.get(allBoards.size() - 1);
        }
        return checkForWinner(board, moveSet);
    }

    public String checkForWinner(Board board, List<String> moveSet){
        List<String[]> winStates = board.getWinStates();
        List<String[]> copyOfWinStates = new ArrayList<>();
        for (String[] winMoves: winStates){
            copyOfWinStates.add(Arrays.copyOf(winMoves, winMoves.length));
        }
        for (String str: moveSet){
            String[] move = str.split(" ", 2);
            for (String[] arrayOfMoves: winStates){
                if (Arrays.asList(arrayOfMoves).contains(move[1])){
                    int index = Arrays.asList(arrayOfMoves).indexOf(move[1]);
                    arrayOfMoves[index] = move[0].equals("CROSS") ? "X" : "O";
                    if (foundWinner(board, arrayOfMoves)) {
                        setWinningMoves(board, copyOfWinStates);
                        return move[0] + " Wins!";
                    }
                }
            }
        }
        return checkForDraw(board, moveSet.size());
    }

    public boolean foundWinner(Board board, String[] movesMade){
        StringBuilder stringOfMoves = new StringBuilder();
        for (String move: movesMade){
            stringOfMoves.append(move);
        }
        String moves = String.valueOf(stringOfMoves);
        if (moves.contains("XXX")) board.setWinner("CROSS");
        else if (moves.contains("OOO")) board.setWinner("CIRCLE");
        else return false;
        gameRepository.save(board);
        return true;
    }

    public void setWinningMoves(Board board, List<String[]> copyOfWinStates){
        List<String[]> movesMade = board.getWinStates();
        String[] winMove = new String[3];
        for (int i=0; i<movesMade.size(); ++i){
            String[] move = movesMade.get(i);
            for (int j=1; j<move.length-1; ++j){
                if (move[j-1].equals(move[j]) && move[j].equals(move[j+1])){
                    String[] found = copyOfWinStates.get(i);
                    winMove = new String[]{ found[j-1], found[j], found[j+1]};
                    break;
                }
            }
        }
        board.setWinningMove(winMove);
        gameRepository.save(board);
    }

    public String checkForDraw(Board board, int numberOfMoves){
        if (numberOfMoves == pow(board.getSize(), 2)){
            board.setWinner("Draw");
            gameRepository.save(board);
            return "Draw";
        }
        return "Match In Progress!";
    }

    @Override
    public void resetStats() {
        // clear database
        newId = 1;
        gameRepository.deleteAll();
    }

    @Override
    public int[] showResults() {
        List<Board> allBoards = gameRepository.findAll();
        int crossWins, circleWins, draw;
        crossWins = circleWins = draw = 0;
        for (Board board: allBoards){
//            switch (board.getWinner()) {
//                case "CROSS" -> ++crossWins;
//                case "CIRCLE" -> ++circleWins;
//                case "Draw" -> ++draw;
//            }
            if (board.getWinner().equals("CROSS")) ++crossWins;
            else if (board.getWinner().equals("CIRCLE")) ++circleWins;
            else if (board.getWinner().equals("Draw")) ++draw;
        }
        return new int[]{crossWins, circleWins, draw};
    }

    @Override
    public String[] getWinningMoves() {
        List<Board> allBoards = gameRepository.findAll();
        Board board = allBoards.get(allBoards.size() - 1);
        return board.getWinningMove();
    }

    public void setWinStates(int size, List<String[]> winStates){
        String[] populate;
        String[] populate2;
        int row, col;

        // adding horizontal and vertical win states
        for (int i=0; i<size; ++i){
            populate = new String[size];
            populate2 = new String[size];
            row = 0;
            col = i;
            for (int j=0; j<size; ++j){
                populate[j] = row+" "+col;
                populate2[j] = col+" "+row;
                ++row;
            }
            winStates.add(populate2);
            winStates.add(populate);
        }

        // adding diagonally right and left win states
        int arraySize = size;
        int len = (size-3)*2 + 1;
        for (int i=0; i<len; ++i){
            populate = new String[arraySize];
            populate2 = new String[arraySize];
            row = 0;
            col = i;
            for (int j=0; j<arraySize; ++j){
                populate[j] = row+" "+col;
                populate2[j] = row+" "+(size-1-col);
                ++row;
                ++col;
            }
            --len;
            --arraySize;
            winStates.add(populate);
            winStates.add(populate2);
        }
        len = (size-3)*2 + 1;
        arraySize = size - 1;
        for (int i=1; i<len; ++i) {
            populate = new String[arraySize];
            populate2 = new String[arraySize];
            row = i;
            col = 0;
            for (int j = 0; j < arraySize; ++j) {
                populate[j] = row + " " + col;
                populate2[j] = row + " " + (size - 1 - col);
                ++row;
                ++col;
            }
            --len;
            --arraySize;
            winStates.add(populate);
            winStates.add(populate2);
        }
    }

}
