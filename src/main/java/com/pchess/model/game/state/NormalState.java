package com.pchess.model.game.state;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.game.GameState;
import com.pchess.model.game.Referee;
import com.pchess.model.pieces.Piece;

public class NormalState implements GameState {

    /**
     * Valida se uma peça pode ser selecionada para movimentação no estado normal.
     * Apenas permite a seleção se a casa contiver uma peça e se ela pertencer ao jogador do turno.
     */
    @Override
    public boolean canSelect(Piece piece, String currentTurn) {
        return piece != null && piece.getColor().equals(currentTurn);
    }

    /**
     * Valida a legalidade de um movimento simulando o resultado antes de aplicá-lo.
     * Garante que a peça obedeça sua geometria de movimento e que o movimento não coloque ou mantenha o próprio Rei do jogador em situação de Xeque.
     */
    @Override
    public boolean isValidMove(Piece piece, Position from, Position to, Board board, Referee referee) {
        
        // Validação Geométrica: Verifica se a peça sabe se mover fisicamente até o destino
        if (!piece.canMove(from, to, board)) {
            return false;
        }

        // Início da Simulação Temporária (Algoritmo de Rollback)
        // Salva a peça que está no destino (pode ser null ou uma peça inimiga capturada)
        Piece capturedPiece = board.getPiece(to);

        // Executa o movimento hipotético na matriz do tabuleiro
        board.setPiece(to, piece);
        board.setPiece(from, null);

        // Validação de Segurança: Pergunta ao CheckAnalyzer se o próprio Rei está a salvo nesta hipótese
        boolean kingSafe = !referee.getCheckAnalyzer().isKingInCheck(piece.getColor(), board);

        // Fim da Simulação (Desfaz as alterações para retornar ao estado original da partida)
        board.setPiece(from, piece);
        board.setPiece(to, capturedPiece);

        // Retorna verdadeiro se o movimento for legal e mantiver o Rei seguro
        return kingSafe;
    }
}