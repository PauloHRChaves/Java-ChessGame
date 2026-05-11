package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.MovementStrategy;

public class PeaoMovement implements MovementStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board, Piece piece) {
        // Padrão: usa o contains que depende do equals() de Position
        return getPossibleMoves(from, board, piece).contains(to);
    }

    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece) {
        List<Position> moves = new ArrayList<>();
        
        int row = currentPos.getRow();
        int col = currentPos.getCol();
        int direction = piece.getColor().equals("white") ? -1 : 1;

        // 1. MOVIMENTO PARA FRENTE (Simples)
        Position nextStep = new Position(row + direction, col);
        if (board.isValidPosition(nextStep) && board.getPiece(nextStep) == null) {
            moves.add(nextStep);

            // 2. MOVIMENTO DUPLO (Apenas se a primeira casa estiver vazia)
            if (!piece.hasMoved()) {
                Position doubleStep = new Position(row + (direction * 2), col);
                if (board.isValidPosition(doubleStep) && board.getPiece(doubleStep) == null) {
                    moves.add(doubleStep);
                }
            }
        }

        // 3. CAPTURAS DIAGONAIS
        checkCaptureDiagonal(moves, row + direction, col - 1, board, piece);
        checkCaptureDiagonal(moves, row + direction, col + 1, board, piece);

        return moves;
    }

    private void checkCaptureDiagonal(List<Position> moves, int r, int c, Board board, Piece piece) {
        Position diagPos = new Position(r, c);
        
        if (board.isValidPosition(diagPos)) {
            Piece target = board.getPiece(diagPos);
            // Peão só move para diagonal se houver inimigo (Regra clássica)
            if (target != null && !target.getColor().equals(piece.getColor())) {
                moves.add(diagPos);
            }
        }
    }
}