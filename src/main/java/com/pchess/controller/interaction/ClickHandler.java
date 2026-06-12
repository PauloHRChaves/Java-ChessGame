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
import com.pchess.model.pieces.PieceFactory;
import com.pchess.model.pieces.concrete.Peao;
import com.pchess.model.pieces.concrete.Rei;
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

        if (selection.isPromotionPending()) {
            return; 
        }

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

            boolean isCastling = pieceToMove instanceof Rei && Math.abs(clickedPos.getCol() - selectedPos.getCol()) == 2;

            if (isCastling) {

                // ROQUE PEQUENO
                if (clickedPos.getCol() == 6) {
                    board.movePiece(
                        new Position(selectedPos.getRow(), 7),
                        new Position(selectedPos.getRow(), 5)
                    );
                }

                // ROQUE GRANDE
                else if (clickedPos.getCol() == 2) {
                    board.movePiece(
                        new Position(selectedPos.getRow(), 0),
                        new Position(selectedPos.getRow(), 3)
                    );
                }
            }

            // Executa a transposição física da peça na matriz do tabuleiro
            board.movePiece(selectedPos, clickedPos);

            boolean ehPeao = pieceToMove.getType().equalsIgnoreCase("peao") || pieceToMove instanceof Peao;
            boolean alcancouFim = (clickedPos.getRow() == 0 || clickedPos.getRow() == 7);

            if (ehPeao && alcancouFim) {
                selection.setPromotionPending(clickedPos);
                selection.clearSelection();
                return; 
            }
            
            // Passa o turno e atualiza o estado de validação de jogadas (Xeque, Xeque-Mate, etc.)
            referee.nextTurn(board);

            playMoveSoundEffect(targetPiece, referee);
            
            // Limpa o estado temporário para a próxima jogada
            selection.clearSelection();
        } else {
            // Regra de Troca Rápida: Se o movimento for inválido, tenta selecionar a nova peça clicada imediatamente
            handleFirstClick(clickedPos);
        }
    }
    
    /**
     * Processa a escolha de promoção do peão, transformando-o na peça selecionada pelo jogador e avançando o turno.
     */
    public void handlePromotionSelection(String tipoEscolhido) {
        SelectionManager selection = session.getSelection();
        Board board = session.getBoard();
        Referee referee = session.getReferee();

        // Coleta dados do estado suspenso na memória
        Position alvo = selection.getPromotionPos();
        String corAtual = referee.getCurrentTurn();

        // Transforma a peça usando o padrão Factory
        Piece novaPeca = PieceFactory.createPiece(tipoEscolhido, corAtual);
        board.setPiece(alvo, novaPeca);

        // Modifica o estado do jogo e avança o turno
        selection.clearPromotion();

        referee.nextTurn(board);

        playMoveSoundEffect(null, referee);

        // Atualiza todos os observers sincronizadamente
        notifier.notify(session);
    }

    // Método auxiliar para tocar efeitos sonoros contextuais após um movimento ser processado
    private void playMoveSoundEffect(Piece targetPiece, Referee referee) {
        if (referee.getCurrentState() instanceof CheckmateState) {
            SoundManager.playCheckmateSound();
        } else if (referee.getCurrentState() instanceof CheckState) {
            SoundManager.playCheckSound();
        } else if (targetPiece != null) {
            SoundManager.playCaptureSound();
        } else {
            SoundManager.playMoveSound();
        }
    }
}