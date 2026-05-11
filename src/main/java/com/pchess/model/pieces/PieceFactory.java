package com.pchess.model.pieces;

import com.pchess.model.pieces.concrete.Bispo;
import com.pchess.model.pieces.concrete.Cavalo;
import com.pchess.model.pieces.concrete.Peao;
import com.pchess.model.pieces.concrete.Rainha;
import com.pchess.model.pieces.concrete.Rei;
import com.pchess.model.pieces.concrete.Torre;

public class PieceFactory {
    public static Piece createPiece(String type, String color) {
        return switch (type) {
            case "bispo" -> new Bispo(color);
            case "peao" -> new Peao(color);
            case "torre" -> new Torre(color);
            case "cavalo" -> new Cavalo(color);
            case "rainha" -> new Rainha(color);
            case "rei" -> new Rei(color);
            default -> null;
        };
    }
}