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
    private final java.util.List<String> moveHistory = new java.util.ArrayList<>();

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
        this.moveHistory.clear();
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

    /**
     * Registra um lance convertido para a notação algébrica que o Stockfish entende.
     */
    public void recordMove(int fromRow, int fromCol, int toRow, int toCol) {
        String from = convertToAlgebraic(fromRow, fromCol);
        String to = convertToAlgebraic(toRow, toCol);
        this.moveHistory.add(from + to); // Ex: adiciona "e2e4" ao histórico
    }

    /**
     * Junta todo o histórico de lances em uma única linha de texto separada por espaços.
     */
    public String getHistoryAsString() {
        return String.join(" ", this.moveHistory);
    }

    /**
     * Converte as coordenadas da matriz JavaFX para o padrão internacional de xadrez.
     */
    private String convertToAlgebraic(int row, int col) {
        char colunaText = (char) ('a' + col);
        int linhaText = 8 - row;
        return "" + colunaText + linhaText;
    }
}