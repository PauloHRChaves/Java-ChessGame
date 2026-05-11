package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.MovementStrategy;

public class RainhaMovement implements MovementStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board, Piece piece) {
        // Padrão unificado: usamos o contains
        return getPossibleMoves(from, board, piece).contains(to);
    }

    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece) {
        List<Position> moves = new ArrayList<>();

        // REAPROVEITAMENTO:
        // Como a Rainha move como Torre + Bispo, apenas chamamos as estratégias delas
        // passando o objeto 'currentPos' (Position)
        moves.addAll(new TorreMovement().getPossibleMoves(currentPos, board, piece));
        moves.addAll(new BispoMovement().getPossibleMoves(currentPos, board, piece));

        return moves;
    }
}