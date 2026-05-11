package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.MovementStrategy;

public class BispoMovement implements MovementStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board, Piece piece) {
        List<Position> moves = getPossibleMoves(from, board, piece);
        return moves.contains(to); 
    }

    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece) {
        List<Position> moves = new ArrayList<>();
        int[] directions = {-1, 1};

        int startRow = currentPos.getRow();
        int startCol = currentPos.getCol();

        for (int dr : directions) {
            for (int dc : directions) {
                int r = startRow + dr;
                int c = startCol + dc;

                Position nextPos = new Position(r, c);

                // board.isValidPosition(nextPos) deve aceitar o objeto Position
                while (board.isValidPosition(nextPos)) {
                    Piece target = board.getPiece(nextPos);

                    if (target == null) {
                        moves.add(nextPos);
                    } else {
                        // Se for peça inimiga, pode capturar, mas para o movimento depois
                        if (!target.getColor().equals(piece.getColor())) {
                            moves.add(nextPos);
                        }
                        break; 
                    }

                    r += dr;
                    c += dc;
                    nextPos = new Position(r, c); 
                }
            }
        }
        return moves;
    }
}