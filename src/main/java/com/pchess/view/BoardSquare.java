package com.pchess.view;

import com.pchess.controller.Orchestrator;
import com.pchess.model.pieces.Piece;

import javafx.scene.layout.StackPane;

public class BoardSquare extends StackPane {
    private final int row;
    private final int col;

    private Piece piece;

    public BoardSquare(int row, int col) {
        this.row = row;
        this.col = col;
        this.getStyleClass().add((row + col) % 2 == 0 ? "square-even" : "square-odd");

        // Fica alerto para cliques no quadrado e notifica o Orquestrator para lidar com a lógica do clique
        this.setOnMouseClicked(event -> {
            Orchestrator.getInstance().handleSquareClick(row, col);
        });
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.getChildren().clear();

        if (piece != null) {
            this.getChildren().add(piece.getView());
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece getPiece() {
        return piece;
    }
}