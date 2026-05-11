package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.MovementStrategy;

public class CavaloMovement implements MovementStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board, Piece piece) {
        // Se o destino está na lista de movimentos possíveis, o movimento é válido.
        return getPossibleMoves(from, board, piece).contains(to);
    }

    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece) {
        List<Position> moves = new ArrayList<>();

        // movimento em "L"
        int[][] offsets = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int[] offset : offsets) {
            int r = currentPos.getRow() + offset[0];
            int c = currentPos.getCol() + offset[1];

            Position targetPos = new Position(r, c);

            if (board.isValidPosition(targetPos)) {
                Piece targetPiece = board.getPiece(targetPos);

                // No xadrez, você pode mover para uma casa vazia 
                // ou capturar uma peça de cor diferente.
                if (targetPiece == null || !targetPiece.getColor().equals(piece.getColor())) {
                    moves.add(targetPos);
                }
            }
        }

        return moves;
    }
}