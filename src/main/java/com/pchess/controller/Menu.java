package com.pchess.controller;

import java.io.IOException;

import javafx.fxml.FXML;

public class Menu {
    // @FXML indica que o método é um manipulador de eventos associado a um arquivo .fxml
    @FXML
    private void onStartGameClicked() throws IOException {
        GameManager.getInstance().startGame();
    }
}