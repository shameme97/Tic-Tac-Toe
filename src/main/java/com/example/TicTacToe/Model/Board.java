package com.example.TicTacToe.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "Board")
public class Board {
    @Id
    private String id;
    private int size;
    private String[] winStates_horizontal;
    private String[] winStates_vertical;
    private String[] winStates_rightDiagonal;
    private String[] winStates_leftDiagonal;
    private String winner;
}
