package com.pchess.controller;

import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;
import com.pchess.model.game.Referee;
import com.pchess.model.game.state.CheckState;
import com.pchess.model.game.state.CheckmateState;
import com.pchess.model.observer.GameObserver;
import com.pchess.view.ChessBoardView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Controlador principal da tela do jogo (game_layout.fxml).
 * Gerencia os componentes visuais e captura as interações do usuário.
 * Implementa GameObserver para atualizar elementos fora do tabuleiro quando o estado muda.
 */
public class GameViewController implements GameObserver {
    private ChessBoardView boardView;
    
    @FXML 
    private BorderPane mainLayout;

    @FXML
    private Label statusLabel;

    @FXML
    private Label turnLabel;
    
    /**
     * Ciclo de vida do JavaFX. Executado automaticamente após o carregamento do FXML.
     * Estabelece a ordem correta de conexões e injeções de dependência para evitar NullPointerExceptions.
     */
    @FXML
    public void initialize() {
        // Obtém as instâncias do microssistema de dados e regras
        GameManager match = GameManager.getInstance();
        GameSession session = match.getSession();

        // Instancia a View injetando este controlador ('this') para mapear os cliques das casas
        this.boardView = new ChessBoardView(this);

        // Acopla visualmente o tabuleiro no centro do layout principal
        mainLayout.setCenter(boardView);

        // Inscreve os observadores na lista de notificações do motor do jogo
        match.addObserver(boardView); // Notificações internas (redesenhar peças e destaques)
        match.addObserver(this);      // Notificações externas (atualizar labels de turno e xeque)

        // Força a primeira renderização visual com o estado inicial da sessão
        boardView.updateFromBoard(session.getBoard(), session.getReferee(), session.getSelection());

        // Atualiza o texto dos labels com base no estado do árbitro
        updateGameStatus(session.getReferee());
    }

    /**
     * Manipulador do evento de clique no botão "Reiniciar".
     * Aciona a Facade para resetar os dados e notificar as telas.
     */
    @FXML
    private void handleRestart() {
        GameManager.getInstance().resetGame();
    }

    /**
     * Captura o clique disparado por qualquer 'BoardSquare' e repassa para a Facade.
     */
    public void squareClick(int row, int col) {
        GameManager.getInstance().handleSquareClick(row, col);
    }

    /**
     * Analisa o estado atual do árbitro (Referee) e atualiza os textos de interface de acordo com as regras de negócio (Turno normal, Xeque ou Xeque-Mate).
     */
    private void updateGameStatus(Referee referee) {
        String turn = referee.getCurrentTurn().equals("white") ? "WHITE" : "BLACK";

        // Aplica polimorfismo/verificação de tipo para identificar o estado da partida
        if (referee.getCurrentState() instanceof CheckmateState) {
            turnLabel.setText("");
            statusLabel.setText("CHECKMATE");
        } else if (referee.getCurrentState() instanceof CheckState) {
            turnLabel.setText(turn);
            statusLabel.setText("- CHECK");
        } else {
            turnLabel.setText(turn);
            statusLabel.setText("");
        }
    }

    /**
     * Resposta do padrão Observer para quando uma jogada válida acontece.
     * Dispara a atualização dos elementos textuais em volta do tabuleiro.
     */
    @Override
    public void onBoardChanged(Board board, SelectionManager selection, Referee referee) {
        updateGameStatus(referee);
    }
}