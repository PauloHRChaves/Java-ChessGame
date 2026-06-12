package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.concrete.Torre;
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

        if (!piece.hasMoved()) {
            checkKingsideCastling(moves, currentPos, board);
            checkQueensideCastling(moves, currentPos, board);
        }

        return moves;
    }

    private void checkKingsideCastling(List<Position> moves, Position reiPos, Board board) {

        int row = reiPos.getRow();

        Position rookPos = new Position(row, 7);

        Piece rook = board.getPiece(rookPos);

        if (!(rook instanceof Torre)) {
            return;
        }

        if (rook.hasMoved()) {
            return;
        }

        if (board.getPiece(new Position(row, 5)) != null) {
            return;
        }

        if (board.getPiece(new Position(row, 6)) != null) {
            return;
        }

        moves.add(new Position(row, 6));
    }
    
    private void checkQueensideCastling(List<Position> moves, Position reiPos, Board board) {

        int row = reiPos.getRow();

        Position rookPos = new Position(row, 0);

        Piece rook = board.getPiece(rookPos);

        if (!(rook instanceof Torre)) {
            return;
        }

        if (rook.hasMoved()) {
            return;
        }

        if (board.getPiece(new Position(row, 1)) != null) {
            return;
        }

        if (board.getPiece(new Position(row, 2)) != null) {
            return;
        }

        if (board.getPiece(new Position(row, 3)) != null) {
            return;
        }

        moves.add(new Position(row, 2));
    }
}