package com.pchess.model.game.rules;

import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.game.Referee;
import com.pchess.model.pieces.Piece;

/**
 * Analisador estatístico e algorítmico de situações de Xeque e Xeque-Mate.
 * Fornece a inteligência lógica necessária para o Referee validar a segurança do Rei e determinar o encerramento da partida.
 */
public class CheckAnalyzer {

    /**
     * Verifica se o Rei da cor informada está sob ataque direto de alguma peça adversária.
     * Varre a matriz procurando peças inimigas e testa se sua trajetória geométrica alcança o Rei.
     */
    public boolean isKingInCheck(String color, Board board) {
        // Localiza a posição atual do Rei alvo no tabuleiro
        Position kingPos = board.findKing(color);

        // Determina a cor do oponente para a varredura
        String opponent = color.equals("white") ? "black" : "white";

        // Varre todas as 64 casas do tabuleiro procurando ameaças
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position current = new Position(row, col);
                Piece piece = board.getPiece(current);

                // Se encontrar uma peça e ela pertencer ao adversário...
                if (piece != null && piece.getColor().equals(opponent)) {

                    // Verifica se a peça consegue atacar fisicamente a casa do Rei
                    if (piece.canMove(current, kingPos, board)) {
                        return true; // Um único ataque confirmado já valida o Xeque
                    }
                }
            }
        }

        return false; // Nenhuma peça inimiga consegue alcançar o Rei
    }

    /**
     * Determina se o jogador atual foi derrotado por Xeque-Mate.
     * O algoritmo simula exaustivamente TODOS os movimentos possíveis de TODAS as peças próprias.
     * Se nenhuma jogada conseguir tirar o Rei do estado de Xeque, confirma-se o Mate.
     */
    public boolean isCheckmate(String color, Board board, Referee referee) {
        // Varre todo o tabuleiro buscando peças do jogador atual
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position from = new Position(row, col);
                Piece piece = board.getPiece(from);

                // Se encontrar uma peça aliada...
                if (piece != null && piece.getColor().equals(color)) {

                    // Obtém a lista geométrica de movimentos teóricos desta peça
                    List<Position> moves = piece.getPossibleMoves(from, board);

                    // Testa cada destino possível através do Árbitro
                    for (Position to : moves) {
                        
                        // O Referee valida o movimento simulando se ele desfaz o Xeque na prática
                        if (referee.isValidMove(piece, from, to, board)) {
                            return false; // Encontrou pelo menos uma jogada de salvação jurídica; Apenas Check
                        }
                    }
                }
            }
        }

        return true; // Nenhuma peça aliada possui movimentos válidos para salvar o Rei; CHECKMATE
    }
}