package com.pchess.model.pieces.concrete;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.concrete_moveset.ReiMovement;

public class Rei extends Piece {

    public Rei(String color) {
        super(color, "rei");
        this.movementStrategy = new ReiMovement();
    }
}