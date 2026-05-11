package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.MovementStrategy;

public class TorreMovement implements MovementStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board, Piece piece) {
        return getPossibleMoves(from, board, piece).contains(to);
    }

    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece) {
        List<Position> moves = new ArrayList<>();
        
        // Direções: Cima, Baixo, Esquerda, Direita
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];
            
            int r = currentPos.getRow() + dr;
            int c = currentPos.getCol() + dc;
            Position nextPos = new Position(r, c);

            while (board.isValidPosition(nextPos)) {
                Piece target = board.getPiece(nextPos);

                if (target == null) {
                    moves.add(nextPos);
                } else {
                    // Se for inimigo, adiciona a casa (captura) e para
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

        return moves;
    }
}