package com.pchess.model.pieces.concrete;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.concrete_moveset.CavaloMovement;

public class Cavalo extends Piece {

    public Cavalo(String color) {
        super(color, "cavalo");
        this.movementStrategy = new CavaloMovement();
    }
}