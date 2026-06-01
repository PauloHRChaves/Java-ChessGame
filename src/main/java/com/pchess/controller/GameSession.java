package com.pchess.controller;

import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;
import com.pchess.model.game.Referee;

/**
 * Representa o estado atual de uma partida ativa de xadrez.
 * Funciona como um contêiner de dados que agrupa o tabuleiro, o "árbitro" e o gerenciador de seleções de casas.
 */
public class GameSession {
    private Board board;
    private Referee referee;
    private SelectionManager selection;

    /**
     * Construtor da sessão.
     * Inicializa os componentes do jogo em seu estado inicial padrão.
     */
    public GameSession() {
        initSession();
    }

    /**
     * Reseta completamente os componentes para iniciar uma nova partida.
     */
    public void reset() {
        initSession();
    }

    /**
     * Método utilitário privado para centralizar a criação dos objetos de estado.
     */
    private void initSession() {
        this.board = new Board();
        this.referee = new Referee();
        this.selection = new SelectionManager();
    }

    // =========================================================================
    // GETTERS
    // =========================================================================

    /**
     * Retorna o tabuleiro físico da partida com a matriz de peças.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retorna o árbitro responsável pelo turno, validações de Xeque e estados do jogo.
     */
    public Referee getReferee() {
        return referee;
    }

    /**
     * Retorna o gerenciador de cliques e destaques de caminhos possíveis.
     */
    public SelectionManager getSelection() {
        return selection;
    }
}