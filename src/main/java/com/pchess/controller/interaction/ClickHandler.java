package com.pchess.controller.interaction;

import java.util.List;

import com.pchess.controller.GameSession;
import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.game.Referee;
import com.pchess.model.game.state.CheckState;
import com.pchess.model.game.state.CheckmateState;
import com.pchess.model.observer.ObserverNotifier;
import com.pchess.model.pieces.Piece;
import com.pchess.utils.SoundManager;

/**
 * Controlador de fluxo para cliques no tabuleiro.
 * Implementa a máquina de estados que diferencia o primeiro clique (seleção da peça) do segundo clique (tentativa de movimento, captura ou cancelamento).
 */
public class ClickHandler {
    private final GameSession session;
    private final ObserverNotifier notifier;

    /**
     * Construtor do manipulador de cliques.
     * Recebe as instâncias de sessão e notificação controladas pela GameManager.
     */
    public ClickHandler(GameSession session, ObserverNotifier notifier) {
        this.session = session;
        this.notifier = notifier;
    }

    /**
     * Ponto de entrada para o processamento de qualquer clique em uma casa.
     * Decide o fluxo com base na existência ou não de uma peça previamente selecionada.
     */
    public void handleClick(int row, int col) {
        Position clickedPos = new Position(row, col);
        SelectionManager selection = session.getSelection();

        // Máquina de estados baseada no SelectionManager
        if (!selection.hasSelection()) {
            handleFirstClick(clickedPos);
        } else {
            handleSecondClick(clickedPos);
        }
        
        // Dispara a atualização visual para todos os observadores após processar o clique
        notifier.notify(session);
    }

    /**
     * Processa o primeiro clique do jogador.
     * Valida se a casa clicada contém uma peça própria e, se sim, calcula os movimentos possíveis.
     */
    private void handleFirstClick(Position position) {
        Board board = session.getBoard();
        Referee referee = session.getReferee();
        SelectionManager selection = session.getSelection();

        Piece piece = board.getPiece(position);

        // O árbitro valida se a peça pertence ao jogador do turno atual
        if (referee.canSelect(piece)) {
            // Delega à peça o cálculo de suas regras geométricas de movimento no tabuleiro
            List<Position> moves = piece.getPossibleMoves(position, board);
            selection.select(position, moves);
        }
    }

    /**
     * Processa o segundo clique do jogador após uma peça já estar selecionada.
     * Trata o cancelamento da seleção, movimentos válidos, capturas de peças inimigas e efeitos sonoros.
     */
    private void handleSecondClick(Position clickedPos) {
        Board board = session.getBoard();
        Referee referee = session.getReferee();
        SelectionManager selection = session.getSelection();

        Position selectedPos = selection.getSelectedPos();

        // Regra de Cancelamento: Clicar na mesma peça selecionada desfaz a seleção
        if (selectedPos.equals(clickedPos)) {
            selection.clearSelection();
            return;
        }

        Piece pieceToMove = board.getPiece(selectedPos);
        
        // O árbitro valida se o movimento proposto respeita as regras e não coloca o próprio rei em xeque
        boolean validMove = referee.isValidMove(pieceToMove, selectedPos, clickedPos, board);

        if (validMove) {
            Piece targetPiece = board.getPiece(clickedPos);

            // Executa a transposição física da peça na matriz do tabuleiro
            board.movePiece(selectedPos, clickedPos);
            pieceToMove.setMoved(); // Altera a flag interna da peça (útil para regras como o roque e primeiro movimento do peão)

            // Passa o turno e atualiza o estado de validação de jogadas (Xeque, Xeque-Mate, etc.)
            referee.nextTurn(board);

            // Orquestração de áudio baseada em captura ou movimento simples
            if (referee.getCurrentState() instanceof CheckmateState) {
                SoundManager.playCheckmateSound();
            } else if (referee.getCurrentState() instanceof CheckState) {
                SoundManager.playCheckSound();
            } else if (targetPiece != null) {
                SoundManager.playCaptureSound();
            } else {
                SoundManager.playMoveSound();
            }
            
            // Limpa o estado temporário para a próxima jogada
            selection.clearSelection();
        } else {
            // Regra de Troca Rápida: Se o movimento for inválido, tenta selecionar a nova peça clicada imediatamente
            handleFirstClick(clickedPos);
        }
    }
}