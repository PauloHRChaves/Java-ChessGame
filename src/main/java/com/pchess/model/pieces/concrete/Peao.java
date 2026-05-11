package com.pchess.model.pieces.concrete;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.concrete_moveset.PeaoMovement;

public class Peao extends Piece {

    public Peao(String color) {
        super(color, "peao");
        this.movementStrategy = new PeaoMovement();
    }
}