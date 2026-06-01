package com.pchess.view;

import com.pchess.controller.GameViewController;
import com.pchess.model.pieces.Piece;

import javafx.scene.layout.StackPane;

/**
 * Representa visualmente uma única casa individual do tabuleiro de xadrez.
 * Estende o StackPane para permitir o empilhamento da imagem da peça sobre o plano de fundo, funcionando como o componente periférico de entrada para cliques do usuário.
 */
public class BoardSquare extends StackPane {
    // Coordenadas imutáveis de matriz desta casa específica
    private final int row;
    private final int col;

    // Referência para a peça atualmente hospedada nesta casa (pode ser null)
    private Piece piece;

    /**
     * Construtor da Casa do Tabuleiro.
     * Define a posição, calcula a cor de fundo padrão e vincula o evento de clique ao controlador.
     * @param row        Índice da linha da casa (0 a 7).
     * @param col        Índice da coluna da casa (0 a 7).
     * @param controller O controlador de eventos da interface (GameViewController).
     */
    public BoardSquare(int row, int col, GameViewController controller) {
        this.row = row;
        this.col = col;
        
        // Algoritmo matemático que intercala o padrão xadrez de cores do tabuleiro (claras e escuras)
        this.getStyleClass().add((row + col) % 2 == 0 ? "square-even" : "square-odd");

        // Configura o listener de eventos de clique do JavaFX para despachar a coordenada ao Controller
        this.setOnMouseClicked(event -> {
            controller.squareClick(row, col);
        });
    }

    /**
     * Sincroniza o estado visual da casa com o modelo físico do jogo.
     * Limpa o nó gráfico anterior e injeta a ImageView da peça fornecida, ou mantém a casa vazia.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        
        // Remove qualquer elemento visual antigo (limpa a imagem da peça anterior)
        this.getChildren().clear();

        // Se houver uma peça nesta coordenada, renderiza seu nó gráfico por cima da casa
        if (piece != null) {
            this.getChildren().add(piece.getView());
        }
    }

    // =========================================================================
    // MÉTODOS DE CONSULTA (Getters)
    // =========================================================================

    /**
     * Retorna o índice numérico da linha desta casa.
     */
    public int getRow() {
        return row;
    }

    /**
     * Retorna o índice numérico da coluna desta casa.
     */
    public int getCol() {
        return col;
    }

    /**
     * Retorna a entidade da peça atualmente posicionada nesta casa.
     */
    public Piece getPiece() {
        return piece;
    }
}