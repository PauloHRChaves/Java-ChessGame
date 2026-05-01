package com.pchess.controller;

import com.pchess.view.ChessBoardView;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class GameViewController {
    @FXML 
    private StackPane rootContainer;
    private ChessBoardView boardView;

    @FXML
    public void initialize() {
        this.boardView = new ChessBoardView();

        // getChildren() é um método do JavaFX
        // Retorna a lista de nós filhos do StackPane, e add(boardView) adiciona o ChessBoardView como um filho, fazendo com que ele seja exibido na interface.
        rootContainer.getChildren().add(boardView);
        
        GameEngine.getInstance().setupGame(boardView);
    }
}