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
    private List<String[]> winStates;
    private String winner;

    public Board(int size, List<String[]> winStates){
        this.size = size;
        this.winStates = winStates;
    }
}
