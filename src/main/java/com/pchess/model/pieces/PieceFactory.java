package com.pchess.model.pieces;

import com.pchess.model.pieces.concrete.Bispo;
import com.pchess.model.pieces.concrete.Cavalo;
import com.pchess.model.pieces.concrete.Peao;
import com.pchess.model.pieces.concrete.Rainha;
import com.pchess.model.pieces.concrete.Rei;
import com.pchess.model.pieces.concrete.Torre;

/**
 * Fábrica centralizada de peças de xadrez (Padrão de Projeto FACTORY METHOD).
 * Isola e encapsula a lógica de instanciação das subclasses concretas de Piece, permitindo que o tabuleiro crie peças a partir de strings simples de configuração.
 */
public class PieceFactory {

    /**
     * Método de fábrica estático que instancia a peça correspondente baseando-se no tipo textual e atribui a cor informada.
     * Utiliza a sintaxe moderna de 'Switch Expression' do Java.
     * @return Uma instância da subclasse concreta de Piece
     */
    public static Piece createPiece(String type, String color) {
        return switch (type.toLowerCase()) {
            case "bispo"  -> new Bispo(color);
            case "peao"   -> new Peao(color);
            case "torre"  -> new Torre(color);
            case "cavalo" -> new Cavalo(color);
            case "rainha" -> new Rainha(color);
            case "rei"    -> new Rei(color);
            default       -> null;
        };
    }
}