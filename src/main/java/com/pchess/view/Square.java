package com.pchess.view;

import com.pchess.model.pieces.Piece;

import javafx.scene.layout.StackPane;

public class Square extends StackPane {
    public int getRow() { return row; }
    public int getCol() { return col; }
    public Piece getPiece() { return piece; }
    
    private final int row, col;
    private Piece piece;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;

        this.getStyleClass().add((row + col) % 2 == 0 ? "casa-par" : "casa-ímpar");
        //? (0 + 0) % 2 == 0 (Par) -> O código aplica o CSS casa-par
        //? (0 + 1) % 2 == 1 (Ímpar) -> O código aplica o CSS casa-ímpar
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.getChildren().clear(); // Remove qualquer coisa anterior
        if (piece != null) {
            this.getChildren().add(piece.getView()); // Adiciona a imagem da peça
        }
    }
}