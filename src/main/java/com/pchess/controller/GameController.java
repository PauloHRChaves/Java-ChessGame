package com.pchess.controller;

import com.pchess.view.ChessBoardView;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

// Vai ser executado quando a interface gráfica for carregada no startGame() do MatchController
public class GameController {
    private ChessBoardView boardView;
    
    @FXML 
    private BorderPane mainLayout;
    
    @FXML
    public void initialize() {
        Orchestrator match = Orchestrator.getInstance();

        this.boardView = new ChessBoardView(match.getSelectionManager());
        
        mainLayout.setCenter(boardView);
        
        match.addObserver(boardView);

        // Linka o tabuleiro da interface gráfica com o tabuleiro do MatchController
        boardView.updateFromBoard(match.getBoard());
    }
}