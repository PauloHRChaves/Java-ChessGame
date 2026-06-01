package com.pchess.model.game;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.game.rules.CheckAnalyzer;
import com.pchess.model.game.state.CheckState;
import com.pchess.model.game.state.CheckmateState;
import com.pchess.model.game.state.NormalState;
import com.pchess.model.pieces.Piece;

/**
 * Representa o Árbitro do jogo.
 * Aplica o padrão de projeto STATE para alterar dinamicamente o comportamento das regras e permissões de seleção/movimento dependendo do estado atual da partida.
 */
public class Referee {
    // Controla de quem é a vez de jogar ("white" ou "black")
    private String currentTurn = "white";
    
    // Estado atual do jogo (Contexto do padrão State)
    private GameState currentState;

    // Motor analítico para detecção de xeque e mate
    private final CheckAnalyzer checkAnalyzer;

    /**
     ** Construtor do Árbitro.
     * Define o estado inicial da partida como Normal e instancia o analisador de xeque.
     */
    public Referee() {
        this.currentState = new NormalState();
        this.checkAnalyzer = new CheckAnalyzer();
    }

    /**
     * Valida se o jogador pode selecionar uma determinada peça.
     * Delega a decisão para o Estado atual (ex: no Xeque, bloqueia peças inválidas).
     */
    public boolean canSelect(Piece piece) {
        return currentState.canSelect(piece, currentTurn);
    }

    /**
     * Valida se o movimento proposto de uma peça é legal perante as leis do xadrez.
     * Delega a validação geométrica e de segurança do rei para as regras do Estado atual.
     */
    public boolean isValidMove(Piece piece, Position from, Position to, Board board) {
        return currentState.isValidMove(piece, from, to, board, this);
    }

    /**
     * Alterna o turno da partida entre Brancas e Pretas e aciona o recálculo do estado.
     */
    public void nextTurn(Board board) {
        currentTurn = currentTurn.equals("white") ? "black" : "white";
        updateGameState(board);
    }

    /**
     * Executa a máquina de estados após a mudança de turno.
     * Usa o CheckAnalyzer para verificar ameaças e transiciona o estado para Normal, Check ou Checkmate.
     */
    private void updateGameState(Board board) {
        // Verifica se o rei do jogador do turno atual sofreu xeque
        if (checkAnalyzer.isKingInCheck(currentTurn, board)) {
            
            // Havendo xeque, verifica se há caminhos de escape para evitar o mate
            if (checkAnalyzer.isCheckmate(currentTurn, board, this)) {
                setState(new CheckmateState());

                // ! Debug
                System.out.println("- Checkmate");
            } else {
                setState(new CheckState());
                
                // ! Debug
                System.out.println("- Check");
            }
        } else {
            // Nenhuma ameaça detectada ao rei, o jogo segue em fluxo comum
            setState(new NormalState());
        }
    }

    // =========================================================================
    // MÉTODOS DE ACESSO E MODIFICAÇÃO (GETTERS & SETTERS)
    // =========================================================================

    /**
     * Altera forçadamente o estado interno da máquina de regras do árbitro.
     */
    public void setState(GameState state) {
        this.currentState = state;
    }

    /**
     * Retorna a cor do jogador que deve agir no turno atual.
     */
    public String getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Retorna o analisador algorítmico associado ao árbitro.
     */
    public CheckAnalyzer getCheckAnalyzer() {
        return checkAnalyzer;
    }

    /**
     * Retorna a instância do estado analítico em que a partida se encontra.
     */
    public GameState getCurrentState() {
        return currentState;
    }
}