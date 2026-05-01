package com.pchess.controller;

import java.io.IOException;

import com.pchess.App;
import com.pchess.model.board.Board;
import com.pchess.view.ChessBoardView;

// Singleton: apenas uma instância de GameEngine é criada e compartilhada
public class GameEngine {
    private static GameEngine instance;
    private final Board board;
    
    // Construtor é chamado no new GameEngine() dentro do getInstance()
    private GameEngine() {
        // Constrói o tabuleiro e as peças, mas não lida com a interface gráfica
        this.board = new Board();
    }

    public static GameEngine getInstance() {
        if (instance == null) instance = new GameEngine();
        return instance;
    }

    // Inicia o jogo, carregando a interface gráfica
    public void startGame() throws IOException {
        App.setRoot("game_layout", "game");
    }

    // Associa o modelo (Board) à visão (ChessBoardView)
    public void setupGame(ChessBoardView view) {
        view.updateFromBoard(this.board);
    }
}