package com.pchess.model.pieces.strategy;

import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;

public interface MovementStrategy {
    boolean canMove(Position from, Position to, Board board, Piece piece);
    
    List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece);
}