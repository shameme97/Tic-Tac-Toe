package com.example.TicTacToe.Service;

import com.example.TicTacToe.Model.Board;
import com.example.TicTacToe.Repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.*;

@Slf4j
@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    @Autowired
    public GameRepository gameRepository;

    public Board createBoard(int size) {
        // create new board object with size & win_states
        List<String[]> winStates = new ArrayList<>();
        setWinStates(size, winStates);
        Board board = new Board(gameRepository.findAll().size()+1, size, winStates,"", "", new String[3]);
        gameRepository.insert(board);
        return board;
    }

    public boolean checkIfEmpty(List<String> moveSet){
        return moveSet.isEmpty();
    }

    @Override
    public String submitMove(int size, List<String> moveSet, boolean newGame) {
        checkIfEmpty(moveSet);
        if (moveSet.isEmpty()) return "";
        Board board;
        if (newGame){
            board = createBoard(size);
        }else{
            List<Board> allBoards = gameRepository.findAll();
            board = allBoards.get(allBoards.size() - 1);
            if (!board.getWinner().equals("")) return ""; // to handle multiple submits after win

        }
        return checkForWinner(board, moveSet);
    }

    public String checkForWinner(Board board, List<String> moveSet){
        List<String[]> winStates = board.getWinStates();
        List<String[]> copyOfWinStates = new ArrayList<>();
        for (String[] winMoves: winStates){
            copyOfWinStates.add(Arrays.copyOf(winMoves, winMoves.length));
        }
        board.setFirstTurn(moveSet.get(0).split(" ", 2)[0]);
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
                // checking if 3 consecutive moves are XXX or OOO
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

    @Override
    public List<String[]> getGameDetails() {
        List<Board> allBoards = gameRepository.findAll();
        List<String[]> gameDetails = new ArrayList<>();
        int gameNo = 1;
        for (Board board: allBoards){
            if (!board.getWinner().equals("")){
                String[] boardDetails = {String.valueOf(gameNo),
                    board.getSize()+" x "+board.getSize(), board.getFirstTurn(), board.getWinner()};
                gameDetails.add(boardDetails);
                ++gameNo;
            }

        }
        return gameDetails;
    }

    @Override
    public String getLastValueOfFirstTurn() {
        List<Board> allBoards = gameRepository.findAll();
        return (allBoards.size()==0) ? "" : allBoards.get(allBoards.size() - 1).getFirstTurn();
    }

    @Override
    public double[] getWinStats() {
        List<Board> allBoards = gameRepository.findAll();
        int totalGames = allBoards.size();
        double[] winStats = calculateWinStats(allBoards);
        for (int i=0; i<winStats.length; ++i){
            winStats[i] = (float) round((winStats[i]/totalGames)*100) ; // calculating percentage
        }
        return winStats;
    }

    public double[] calculateWinStats(List<Board> allBoards){
        int cross1stTurn, cross2ndTurn, circle1stTurn, circle2ndTurn;
        cross1stTurn = cross2ndTurn = circle1stTurn = circle2ndTurn = 0;
        for (Board board: allBoards){
            if (board.getWinner().equals("CROSS")){
                if (board.getFirstTurn().equals("CROSS"))
                    ++cross1stTurn;
                else
                    ++cross2ndTurn;
            }
            else if (board.getWinner().equals("CIRCLE")){
                if (board.getFirstTurn().equals("CIRCLE"))
                    ++circle1stTurn;
                else
                    ++circle2ndTurn;
            }
        }
        return new double[]{cross1stTurn, cross2ndTurn, circle1stTurn, circle2ndTurn};
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
