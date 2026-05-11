package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.MovementStrategy;

public class ReiMovement implements MovementStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board, Piece piece) {
        // Usa o padrão de lista + contains
        return getPossibleMoves(from, board, piece).contains(to);
    }

    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece) {
        List<Position> moves = new ArrayList<>();
        int row = currentPos.getRow();
        int col = currentPos.getCol();

        // As 8 direções ao redor do Rei
        int[][] offsets = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };

        for (int[] offset : offsets) {
            Position targetPos = new Position(row + offset[0], col + offset[1]);

            if (board.isValidPosition(targetPos)) {
                Piece target = board.getPiece(targetPos);

                // Movimento básico: casa vazia ou peça inimiga
                if (target == null || !target.getColor().equals(piece.getColor())) {
                    // Nota: Futuramente, aqui checaremos se targetPos está sob ataque
                    moves.add(targetPos);
                }
            }
        }

        // --- Lógica de Castling (Roque) ---
        // Se você quiser começar a esboçar o Roque, seria algo assim:
        // if (!piece.hasMoved() && !isInCheck(board, piece)) {
        //    checkKingsideRoque(moves, currentPos, board, piece);
        //    checkQueensideRoque(moves, currentPos, board, piece);
        // }

        return moves;
    }
}