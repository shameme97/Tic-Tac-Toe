package com.example.TicTacToe.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@Document(collection = "player")
public class Player {
    @Id
    private String id;
    public ArrayList<int[]> moves = new ArrayList<>();
}
