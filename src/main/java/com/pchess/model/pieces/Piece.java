package com.pchess.model.pieces;

import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.flyweight.ImageCache;
import com.pchess.model.pieces.strategy.MovementStrategy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe abstrata base que representa uma peça genérica de xadrez.
 * Aplica o padrão de projeto STRATEGY para delegar o cálculo de caminhos e validações de movimentos para algoritmos de comportamento específicos (MovementStrategy).
 */
public abstract class Piece {
    private final String color;
    private final String type;
    
    // Flag de controle histórico (vital para regras como o Roque do Rei/Torre e o primeiro salto do Peão)
    private boolean moved = false;
    
    // A estratégia de movimento injetada pelas subclasses concretas (Padrão Strategy)
    protected MovementStrategy movementStrategy;

    /**
     * Construtor da Peça.
     * Define a cor ("white"/"black") e o tipo da peça ao ser instanciada pela Factory.
     */
    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Verifica se a peça já realizou algum movimento desde o início da partida.
     */
    public boolean hasMoved() { 
        return moved; 
    }

    /**
     * Registra de forma permanente que a peça executou uma jogada.
     */
    public void setMoved() { 
        this.moved = true; 
    }

    /**
     * Valida de forma pontual se a peça consegue se mover geometricamente da origem ao destino.
     * Delega a decisão em tempo de execução para a estratégia de movimento acoplada.
     */
    public boolean canMove(Position from, Position to, Board board) {
        return movementStrategy.canMove(from, to, board, this);
    }

    /**
     * Varre e retorna a lista de todas as coordenadas geométricas válidas para onde esta peça pode ir.
     * Delega a varredura e os loops de caminho para a estratégia de movimento acoplada.
     */
    public List<Position> getPossibleMoves(Position currentPos, Board board) {
        return movementStrategy.getPossibleMoves(currentPos, board, this);
    }

    // =========================================================================
    // GETTERS
    // ========================================================================= */

    /**
     * Retorna o identificador textual do tipo da peça (ex: "peao", "rei", ...).
     */
    public String getType() { 
        return type; 
    }

    /**
     * Retorna a cor de alinhamento da peça ("white" ou "black").
     */
    public String getColor() { 
        return color; 
    }

    /**
     * Fábrica visual que monta e retorna o componente gráfico da peça para o JavaFX.
     * Utiliza o cache estático 'ImageCache' para evitar releituras redundantes do disco rígido.
     */
    public ImageView getView() {
        // Monta o caminho dinâmico do recurso (ex: "/images/white_peao.png")
        String path = "/images/" + color + "_" + type + ".png";

        // Recupera a instância da imagem em memória usando o Cache (Otimização Flyweight)
        Image image = ImageCache.getImage(getClass().getResource(path).toExternalForm());
        
        // Instancia o nó visual do JavaFX que renderiza o arquivo bitmap na tela
        ImageView view = new ImageView(image);

        // Padroniza as restrições de escala visual para se ajustar perfeitamente ao grid do tabuleiro
        view.setFitWidth(100);
        view.setPreserveRatio(true);

        return view;
    }
}