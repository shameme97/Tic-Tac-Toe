package com.example.TicTacToe.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

}
