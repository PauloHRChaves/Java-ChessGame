package com.pchess.model.pieces.concrete;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.concrete_moveset.BispoMovement;

/**
 * Representa a peça concreta do Bispo no xadrez.
 * Subclasse de Piece que define sua identidade visual/textual e injeta a estratégia de movimentação diagonal (BispoMovement) via herança.
 */
public class Bispo extends Piece {

    /**
     ** Construtor do Bispo.
     * Inicializa os atributos fundamentais na superclasse e injeta a estratégia de movimento.
     */
    public Bispo(String color) {
        // Chama o construtor da classe pai (Piece) para definir a cor e o tipo da peça, garantindo que os atributos privados da superclasse sejam inicializados.
        super(color, "bispo");

        // Aplica o padrão de projeto STRATEGY: acopla o algoritmo específico de deslocamento diagonal e detecção de obstáculos para o Bispo.
        this.movementStrategy = new BispoMovement();
    }
}