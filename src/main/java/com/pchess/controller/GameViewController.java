package com.pchess.controller;

import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;
import com.pchess.model.game.Referee;
import com.pchess.model.game.state.CheckState;
import com.pchess.model.game.state.CheckmateState;
import com.pchess.model.observer.GameObserver;
import com.pchess.view.ChessBoardView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Controlador principal da tela do jogo (game_layout.fxml).
 * Gerencia os componentes visuais e captura as interações do usuário.
 * Implementa GameObserver para atualizar elementos fora do tabuleiro quando o estado muda.
 */
public class GameViewController implements GameObserver {
    private ChessBoardView boardView;
    
    @FXML 
    private BorderPane mainLayout;

    @FXML private Label statusLabel;
    @FXML private Label turnLabel;

    @FXML
    private StackPane boardAnchor;

    @FXML
    private VBox promotionOverlay;

    @FXML private ImageView queenPromoImage;
    @FXML private ImageView rookPromoImage;
    @FXML private ImageView bishopPromoImage;
    @FXML private ImageView knightPromoImage;

    @FXML
    private StackPane rootContainer;
    
    @FXML
    private VBox pauseMenu;
        
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

        boardAnchor.getChildren().add(0, boardView);

        // Inscreve os observadores na lista de notificações do motor do jogo
        match.addObserver(boardView); // Notificações internas (redesenhar peças e destaques)
        match.addObserver(this);      // Notificações externas (atualizar labels de turno e xeque)

        // Força a primeira renderização visual com o estado inicial da sessão
        boardView.updateFromBoard(session.getBoard(), session.getReferee(), session.getSelection());

        // Atualiza o texto dos labels com base no estado do árbitro
        updateGameStatus(session.getReferee());

        // Configura o listener global para a tecla ESC para abrir/fechar o menu de pausa
        rootContainer.setFocusTraversable(true);

        // O listener é adicionado ao nível da cena para garantir que funcione mesmo quando o foco estiver em outros elementos da interface
        rootContainer.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        togglePauseMenu();
                        event.consume();
                    }
                });
            }
        });
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

        if (selection.isPromotionPending()) {
            // Descobre a cor do jogador que está promovendo o peão
            String cor = referee.getCurrentTurn();

            // Carrega e define as imagens dinamicamente baseado na cor
            // (Ajuste o caminho "images/" se seu projeto usar subpastas diferentes na build)
            queenPromoImage.setImage(new Image(getClass().getResourceAsStream("/images/" + cor + "_rainha.png")));
            rookPromoImage.setImage(new Image(getClass().getResourceAsStream("/images/" + cor + "_torre.png")));
            bishopPromoImage.setImage(new Image(getClass().getResourceAsStream("/images/" + cor + "_bispo.png")));
            knightPromoImage.setImage(new Image(getClass().getResourceAsStream("/images/" + cor + "_cavalo.png")));

            // Lembra que salvamos as strings em minúsculo na lógica? Vamos embutir essa info nos botões
            // para o handlePromotionChoice saber quem é quem mesmo sem texto visível:
            ((Button) queenPromoImage.getParent()).setUserData("rainha");
            ((Button) rookPromoImage.getParent()).setUserData("torre");
            ((Button) bishopPromoImage.getParent()).setUserData("bispo");
            ((Button) knightPromoImage.getParent()).setUserData("cavalo");

            promotionOverlay.setVisible(true);
        } else {
            promotionOverlay.setVisible(false);
        }
    }

    /**
     * Manipulador do evento de clique nos botões de promoção
     */
    @FXML
    private void handlePromotionChoice(ActionEvent event) {
        Button btnClicado = (Button) event.getSource();
        
        String tipoEscolhido = (String) btnClicado.getUserData();

        GameManager.getInstance().handlePromotionChoice(tipoEscolhido);
    }

    // Método utilitário para alternar a visibilidade do menu de pausa
    private void togglePauseMenu() {
        // Se a promoção de peão estiver ativa, talvez seja bom bloquear o pause, ou vice-versa.
        // Inverte o estado de visibilidade do overlay do menu de pausa
        boolean isVisible = pauseMenu.isVisible();
        pauseMenu.setVisible(!isVisible);
        
        // Se o menu apareceu, joga o foco para ele para capturar interações
        if (!isVisible) {
            pauseMenu.requestFocus();
        } else {
            rootContainer.requestFocus();
        }
    }

    // Método utilitário público para permitir que o Menu feche a si mesmo
    public void hidePauseMenu() {
        if (pauseMenu != null) {
            pauseMenu.setVisible(false);
            rootContainer.requestFocus();
        }
    }
}