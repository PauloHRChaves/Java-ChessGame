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
            // Linha 0: Peças maiores
            pieces[0][col] = PieceFactory.createPiece(rowOrder[col], "black");
            // Linha 1: Peões
            pieces[1][col] = PieceFactory.createPiece("peao", "black");

            //? --- PEÇAS BRANCAS ---
            // Linha 6: Peões
            pieces[6][col] = PieceFactory.createPiece("peao", "white");
            // Linha 7: Peças maiores
            pieces[7][col] = PieceFactory.createPiece(rowOrder[col], "white");
        }
    }

    public Piece getPiece(int row, int col) {
        return pieces[row][col];
    }
}