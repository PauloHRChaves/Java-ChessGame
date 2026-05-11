package com.pchess.model.board;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.PieceFactory;

public class Board {
    private final Piece[][] pieces = new Piece[8][8];

    public Board() {
        setupInitialPieces();
    }

    private void setupInitialPieces() {
        String[] rowOrder = {"torre", "cavalo", "bispo", "rainha", "rei", "bispo", "cavalo", "torre"};

        for (int col = 0; col < 8; col++) {
            //? --- PEÇAS PRETAS ---
            pieces[0][col] = PieceFactory.createPiece(rowOrder[col], "black");
            pieces[1][col] = PieceFactory.createPiece("peao", "black");
            
            //? --- PEÇAS BRANCAS ---
            pieces[6][col] = PieceFactory.createPiece("peao", "white");
            pieces[7][col] = PieceFactory.createPiece(rowOrder[col], "white");
        }
    }

    public Piece getPiece(int row, int col) {
        if (!isValidPosition(row, col)) {
            return null;
        }
        return pieces[row][col];
    }

    public Piece getPiece(Position position) {
        if (position == null || !isValidPosition(position)) {
            return null;
        }
        return pieces[position.getRow()][position.getCol()];
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public boolean isValidPosition(Position position) {
        if (position == null) return false;
        return isValidPosition(position.getRow(), position.getCol());
    }

    public void setPiece(Position position, Piece piece) {
        if (position != null && isValidPosition(position)) {
            pieces[position.getRow()][position.getCol()] = piece;
        }
    }

    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        setPiece(to, piece);
        setPiece(from, null);
    }

    //! Debug
    public String toCoords(Position position) {
        char column = (char) (position.getCol() + 'A');
        int line = 8 - position.getRow();
        return "" + column + line;
    }
}