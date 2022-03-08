package com.example.TicTacToe.Repository;

import com.example.TicTacToe.Model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Board, String> {
}
