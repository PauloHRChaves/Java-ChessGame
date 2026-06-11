package com.pchess.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Menu {

    @FXML
    private VBox pauseOverlay;

    /**
     * Ação: Inicia a partida Player vs Player (Muda para a tela do tabuleiro).
     */
    @FXML
    private void onStartPvPClicked() throws IOException {
        GameManager.getInstance().startGame();
    }

    /**
     * Ação: Fecha o jogo de forma segura, encerrando a Thread do JavaFX.
     */
    @FXML
    private void handleExitApplication() {
        Platform.exit();
        System.exit(0);
    }

    // =========================================================================
    // MÉTODOS DO MENU DE PAUSA
    // =========================================================================

    @FXML
    private void handleResumeGame() {
        if (pauseOverlay != null) {
            pauseOverlay.setVisible(false);
        }
    }

    @FXML
    private void handleRestart() {
        GameManager.getInstance().resetGame();
        handleResumeGame();
    }

    @FXML
    private void handleGoToMainMenu() throws IOException {
        GameManager.getInstance().resetGame();
        GameManager.getInstance().showMainMenu();
    }
}