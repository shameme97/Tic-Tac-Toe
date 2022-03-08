package com.example.TicTacToe.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "Board")
public class Board {
    @Id
    private String id;
    private int size;
//    private List<String[]> winStates_horizontal;
//    private List<String[]> winStates_vertical;
//    private List<String[]> winStates_rightDiagonal;
//    private List<String[]> winStates_leftDiagonal;
    private List<String[]> winStates;
    private String winner;

//    public Board(int size, List<String[]> winStates_horizontal, List<String[]> winStates_vertical,
//                 List<String[]> winStates_rightDiagonal, List<String[]> winStates_leftDiagonal){
//        this.size = size;
//        this.winStates_horizontal = winStates_horizontal;
//        this.winStates_vertical = winStates_vertical;
//        this.winStates_rightDiagonal = winStates_rightDiagonal;
//        this.winStates_leftDiagonal = winStates_leftDiagonal;
//    }

    public Board(int size, List<String[]> winStates){
        this.size = size;
        this.winStates = winStates;
    }
}
