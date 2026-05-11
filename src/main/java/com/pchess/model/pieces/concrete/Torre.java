package com.pchess.model.pieces.concrete;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.concrete_moveset.TorreMovement;

public class Torre extends Piece {

    public Torre(String color) {
        super(color, "torre");
        this.movementStrategy = new TorreMovement();
    }
}