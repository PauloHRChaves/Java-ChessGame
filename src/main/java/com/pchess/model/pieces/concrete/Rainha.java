package com.pchess.model.pieces.concrete;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.concrete_moveset.RainhaMovement;

public class Rainha extends Piece {

    public Rainha(String color) {
        super(color, "rainha");
        this.movementStrategy = new RainhaMovement();
    }
}