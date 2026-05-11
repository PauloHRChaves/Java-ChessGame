package com.pchess.model.pieces.concrete;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.concrete_moveset.BispoMovement;

//* É uma Subclasse (Filha) de Piece. 
//* Ela herda todas as propriedades e métodos de Piece, mas também pode ter suas próprias características específicas.
public class Bispo extends Piece {

    public Bispo(String color) {
        // Chama o construtor da classe pai (Piece) para definir a cor e o tipo da peça,
        // garantindo que os atributos privados da superclasse sejam inicializados.
        super(color, "bispo");

        // Define a estratégia de movimento específica para o peça.
        this.movementStrategy = new BispoMovement();
    }
}