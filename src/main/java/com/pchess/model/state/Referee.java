package com.pchess.model.state;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;

public class Referee {
    private String currentTurn = "white";

    public String getCurrentTurn() {
        return currentTurn;
    }

    // Verifica se a peça pertence ao jogador atual
    public boolean canSelect(Piece piece) {
        if (piece == null) {
            return false;
        }
        
        return piece.getColor().equals(currentTurn);
    }

    public boolean isValidMove(Piece piece, Position from, Position to, Board board) {
        if (piece == null) {
            return false;
        }
        return piece.canMove(from, to, board);
    }

    // Troca o turno
    public void nextTurn() {
        currentTurn = currentTurn.equals("white") ? "black" : "white";
    }
}