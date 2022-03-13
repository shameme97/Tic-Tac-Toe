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
        Board board = new Board(newId, size, winStates, "");
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
        for (String str: moveSet){
            String[] move = str.split(" ", 2);
            for (String[] arrayOfMoves: winStates){
                if (Arrays.asList(arrayOfMoves).contains(move[1])){
                    int index = Arrays.asList(arrayOfMoves).indexOf(move[1]);
                    arrayOfMoves[index] = move[0].equals("CROSS") ? "X" : "O";
                    if (foundWinner(board, arrayOfMoves))
                        return move[0] + " Wins!";
                }
            }
        }
        // check if draw or match in progress
        return (moveSet.size()==pow(board.getSize(), 2)) ? matchIsDraw(board) : "Match In Progress!";
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

    public String matchIsDraw(Board board){
        board.setWinner("Draw");
        gameRepository.save(board);
        return "Draw";
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
            switch (board.getWinner()) {
                case "CROSS" -> ++crossWins;
                case "CIRCLE" -> ++circleWins;
                case "Draw" -> ++draw;
            }
        }
        return new int[]{crossWins, circleWins, draw};
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
