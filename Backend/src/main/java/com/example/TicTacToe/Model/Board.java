package com.example.TicTacToe.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Board")
public class Board {
    @Id
    private int id;
    private int size;
    private List<String[]> winStates;
    private String firstTurn;
    private String winner;
    private String[] winningMove;
    private int numberOfMoves;
    private String player1;
    private String player2;

    public Board(int id, int size, List<String[]> winStates, String player1, String player2){
        this.id = id;
        this.size = size;
        this.winStates = winStates;
        this.player1 = player1;
        this.player2 = player2;
    }
}
