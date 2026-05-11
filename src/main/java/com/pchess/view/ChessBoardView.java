package com.pchess.view;

import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.observer.GameObserver;
import com.pchess.model.pieces.Piece;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

// O ChessBoardView é responsável pela renderização do tabuleiro e das peças, bem como pela atualização visual com base no estado do jogo
public class ChessBoardView extends GridPane implements GameObserver {
    private final BoardSquare[][] squares = new BoardSquare[8][8];
    private final SelectionManager selection;

    private static boolean isFirstUpdate = true;

    public ChessBoardView(SelectionManager selection) {
        this.selection = selection;
        setupGrid();
        createSquares();
    }

    private void setupGrid() {
        this.getStyleClass().add("board");
        this.setMinSize(868, 868);
        this.setPrefSize(904, 904);

        // Configura o layout do GridPane para ter 8 colunas e 8 linhas, cada uma ocupando 12.5% do espaço total, 8 x 12.5% = 100%
        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(12.5);
            this.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(12.5);
            this.getRowConstraints().add(rowConst);
        }
    }

    private void createSquares() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                BoardSquare square =  new BoardSquare(row, col);
                squares[row][col] = square;
                this.add(square, col, row);
            }
        }
    }

    // Atualiza a visualização do tabuleiro com base no estado atual do Board e do SelectionManager
    public void updateFromBoard(Board board) {
        if (!isFirstUpdate) {
            clearAllHighlights();
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Para cada posição, obtém a peça correspondente do Board
                Piece piece = board.getPiece(row, col);
                // Atualiza a IMAGEM da peça exibida na casa do tabuleiro com base no estado atual do Board
                squares[row][col].setPiece(piece);
            }
        }

        isFirstUpdate = false;

        // Se não houver uma seleção ativa, não há necessidade de destacar nada
        if (!selection.hasSelection()) {
            return;
        }

        // Destaca a casa selecionada e os movimentos possíveis, se houver uma seleção ativa
        if (selection.hasSelection()) {
            // Casa selecionada
            Position selectSquare = selection.getSelectedPos();
            squares[selectSquare.getRow()][selectSquare.getCol()].getStyleClass().add("selectedSquare");

            // Movimentos possíveis da peça selecionada
            for (Position move : selection.getPossibleMoves()) {
                BoardSquare targetSquare = squares[move.getRow()][move.getCol()];

                // Destaca a casa de destino do movimento possível
                if (board.getPiece(move) != null) {
                    targetSquare.getStyleClass().add("possible-capture");
                } else {
                    targetSquare.getStyleClass().add("possible-move");
                }
            }
        }
    }

    // Remove os estilos de destaque de seleção e movimentos possíveis de todas as casas do tabuleiro
    public void clearAllHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].getStyleClass().removeAll("selectedSquare", "possible-move", "possible-capture");
            }
        }
    }

    @Override
    public void onBoardChanged(Board board, SelectionManager selection) {
        updateFromBoard(board);
    }
}