package com.pchess.model.pieces;

import com.pchess.model.pieces.obj.Bispo;
import com.pchess.model.pieces.obj.Cavalo;
import com.pchess.model.pieces.obj.Peao;
import com.pchess.model.pieces.obj.Rainha;
import com.pchess.model.pieces.obj.Rei;
import com.pchess.model.pieces.obj.Torre;

// Factory: Cria peças com base em uma string de tipo e cor, centralizando a lógica de criação.
public class PieceFactory {
    public static Piece createPiece(String type, String color) {
        return switch (type.toLowerCase()) {
            case "peao" -> new Peao(color);
            case "torre" -> new Torre(color);
            case "cavalo" -> new Cavalo(color);
            case "bispo" -> new Bispo(color);
            case "rainha" -> new Rainha(color);
            case "rei" -> new Rei(color);
            default -> throw new IllegalArgumentException("Peça desconhecida: " + type);
        };
    }
}