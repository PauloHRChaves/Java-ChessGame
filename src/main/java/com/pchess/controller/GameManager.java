package com.pchess.controller;

import java.io.IOException;

import com.pchess.App;
import com.pchess.controller.interaction.ClickHandler;
import com.pchess.model.observer.GameObserver;
import com.pchess.model.observer.ObserverNotifier;

/**
 * Gerenciador principal do jogo.
 * Aplica o padrão SINGLETON (garante uma única instância global) e o padrão FACADE (centraliza e simplifica o acesso aos subsistemas do jogo).
 */
public class GameManager {
    private static GameManager instance;
    
    private final GameSession session;
    private final ObserverNotifier notifier;
    private final ClickHandler clickHandler;

    /**
     * Construtor privado para impedir instanciação externa direta.
     * Inicializa todos os subsistemas essenciais na criação do gerenciador.
     */
    private GameManager() {
        this.session = new GameSession();
        this.notifier = new ObserverNotifier();
        this.clickHandler = new ClickHandler(session, notifier);
    }

    /**
     * Retorna a instância única do GameManager.
     * O uso de 'synchronized' garante seguranç a para execução em múltiplas threads.
     */
    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // =========================================================================
    // MÉTODOS FACADE (Delegação de responsabilidades para os subsistemas)
    // =========================================================================

    /**
     * Altera a tela do aplicativo para a interface do jogo de xadrez.
     */
    public void startGame() throws IOException {
        App.setRoot("game_layout", "game");
    }

    /**
     * Altera a tela do aplicativo de volta para o menu principal.
     */
    public void showMainMenu() throws IOException {
        App.setRoot("menu", "menu");
    }

    /**
     * Delega o processamento do clique de uma casa para o manipulador de cliques.
     */
    public void handleSquareClick(int row, int col) {
        clickHandler.handleClick(row, col);
    }
    
    /**
     * Delega a inscrição de novos ouvintes (telas) para o sistema de notificações.
     */
    public void addObserver(GameObserver observer) {
        notifier.add(observer);
    }
  
    /**
     * Expõe o estado da sessão atual para leitura de dados (tabuleiro, árbitro, etc).
     */
    public GameSession getSession() {
        return session;
    }

    /**
     * Delega o processamento da escolha de promoção do peão para o manipulador de cliques.
     */
    public void handlePromotionChoice(String tipoEscolhido) {
        this.clickHandler.handlePromotionSelection(tipoEscolhido);
    }

    /**
     * Reinicia o estado das regras/peças e força a notificação para atualizar a View.
     */
    public void resetGame() {
        session.reset();
        notifier.notify(session);
    }
}