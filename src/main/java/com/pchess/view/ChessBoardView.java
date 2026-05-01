package com.pchess.view;

import com.pchess.model.board.Board;
import com.pchess.model.pieces.Piece;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

// Composite: ChessBoardView é composto por 64 Squares, e cada Square pode conter uma Piece
public class ChessBoardView extends GridPane {
    private final Square[][] squares = new Square[8][8];
    
    public ChessBoardView() {
        this.getStyleClass().add("tabuleiro");
        this.setMinSize(700, 700);
        this.setMaxSize(950, 950);

        // Lógica de criação dos 64 Squares, usando uma matriz
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = new Square(row, col);
                squares[row][col] = square; // Salva na matriz
                this.add(square, col, row);
            }
        }

        // Componentes do JavaFX 
        //? O método setHgrow(Priority.ALWAYS) e setVgrow(Priority.ALWAYS) garantem que as colunas e linhas cresçam para preencher o espaço disponível, mantendo a proporção correta.
        //? O método setPercentWidth(12.5) e setPercentHeight(12.5) garantem que cada coluna e linha ocupe exatamente 12.5% do espaço total, o que é essencial para manter a aparência de um tabuleiro de xadrez.
        //? O loop for é usado para configurar cada uma das 8 colunas e 8 linhas do GridPane, garantindo que o layout seja responsivo e que cada Square se ajuste corretamente ao tamanho do tabuleiro.
        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.ALWAYS); // Faz a coluna crescer
            colConst.setPercentWidth(12.5);
            this.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setVgrow(Priority.ALWAYS); // Faz a linha crescer
            rowConst.setPercentHeight(12.5);
            this.getRowConstraints().add(rowConst);
        }
    }

    // Ele garante que o visual seja uma cópia fiel da lógica do tabuleiro, atualizando cada Square com a Piece correspondente do Board
    public void updateFromBoard(Board board) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece position = board.getPiece(r, c);
                this.getSquare(r, c).setPiece(position);
            }
        }
    }

    // Encontrar um Square específico dentro da matriz, usando as coordenadas de linha e coluna. Ele retorna o Square correspondente ou null se as coordenadas forem inválidas
    public Square getSquare(int row, int col) {
        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            return squares[row][col];
        }
        return null;
    }
}