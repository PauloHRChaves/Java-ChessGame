package com.pchess.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pchess.App;
import com.pchess.controller.interaction.ClickHandler;
import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;
import com.pchess.model.observer.GameObserver;
import com.pchess.model.state.Referee;

public class Orchestrator {
    private static Orchestrator instance;
    private final Board board;
    private final Referee referee;
    private final SelectionManager selectionManager;
    private final ClickHandler clickHandler;

    // Lista de observadores para notificar mudanças no tabuleiro
    private final List<GameObserver> observers = new ArrayList<>();

    // Método para obter a instância única do Orquestrator
    public static Orchestrator getInstance() {
        if (instance == null) {
            instance = new Orchestrator();
        }

        return instance;
    }

    // Construtor é chamado no new Orquestrator() dentro do getInstance()
    private Orchestrator() {
        this.board = new Board();
        this.referee = new Referee();
        this.selectionManager = new SelectionManager();
        this.clickHandler = new ClickHandler(board, referee, selectionManager, this);
    }

    // Inicia a partida, carregando a interface gráfica
    public void startGame() throws IOException {
        App.setRoot("game_layout", "game");
    }

    // Retorna o gerenciador de seleção para ser usado na interface gráfica
    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    // Manipula o clique no tabuleiro
    public void handleSquareClick(int row, int col) {
        clickHandler.handleClick(row, col);
    }

    // Adiciona o tabuleiro no observador para que ele possa ser notificado sobre mudanças do jogo
    public void addObserver(GameObserver boardView) {
        observers.add(boardView);
    }

    // Notifica o observador sobre mudanças no tabuleiro, passando o estado atual do tabuleiro e o gerenciador de seleção
    public void notifyBoardChanged() {
        for (GameObserver observer : observers) {
            observer.onBoardChanged(board, selectionManager);
        }
    }

    // GETTERS
    public Board getBoard() {
        return board;
    }
}