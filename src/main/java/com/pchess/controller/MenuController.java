package com.pchess.controller;

import java.io.IOException;

import javafx.fxml.FXML;

public class MenuController {
    // @FXML é uma anotação do JavaFX que indica que o método é um manipulador de eventos associado a um elemento da interface gráfica.
    @FXML
    private void onStartGameClicked() throws IOException {

        // Inicia o getInstance() do MatchController e depois chama startGame() para iniciar o jogo.
        Orchestrator.getInstance().startGame();
    }
}