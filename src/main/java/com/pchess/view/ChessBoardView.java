package com.pchess.view;

import com.pchess.controller.GameViewController;
import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.game.Referee;
import com.pchess.model.game.state.CheckState;
import com.pchess.model.game.state.CheckmateState;
import com.pchess.model.observer.GameObserver;
import com.pchess.model.pieces.Piece;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Representação visual e gráfica do tabuleiro de xadrez usando JavaFX.
 * Estende o GridPane para gerenciar o layout de quadrantes e implementa GameObserver para reagir automaticamente a qualquer mudança no estado da partida (Padrão Observer).
 */
public class ChessBoardView extends GridPane implements GameObserver {
    // Matriz de espelhamento visual contendo os nós gráficos de cada casa
    private final BoardSquare[][] squares = new BoardSquare[8][8];
    
    private final GameViewController controller;

    // Flag de controle para ignorar a limpeza de destaques na renderização inicial
    private boolean isFirstUpdate = true;

    /**
     * Construtor da View do Tabuleiro.
     * Configura as restrições geométricas do grid e popula as 64 casas visuais.
     * @param controller O controlador de interface que receberá as interações do usuário.
     */
    public ChessBoardView(GameViewController controller) {
        this.controller = controller;
        setupGrid();
        createSquares();
    }

    /**
     * Configura a estrutura matemática do GridPane.
     * Divide o espaço em um grid simétrico de 8x8, onde cada linha e coluna ocupa exatamente 12.5%.
     */
    private void setupGrid() {
        this.getStyleClass().add("board");
        this.setPrefSize(910, 910);

        // Aplica restrições de escala percentual para manter a proporção ao redimensionar
        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(12.5);
            this.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(12.5);
            this.getRowConstraints().add(rowConst);
        }
    }

    /**
     * Popula o grid injetando as 64 instâncias de BoardSquare na matriz visual.
     * Mapeia os índices de renderização para o gerenciador de layout do JavaFX.
     */
    private void createSquares() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                BoardSquare square = new BoardSquare(row, col, controller);
                squares[row][col] = square;
                
                // Adiciona o nó ao GridPane respeitando a ordem (componente, coluna, linha)
                this.add(square, col, row);
            }
        }
    }

    /**
     * Motor principal de renderização e atualização visual do tabuleiro.
     * Sincroniza a posição das peças físicas com as imagens e altera dinamicamente os estilos CSS para destacar seleções, movimentos válidos, capturas, xeque e xeque-mate.
     */
    public void updateFromBoard(Board board, Referee referee, SelectionManager selection) {
        // Remove destaques antigos de movimentos anteriores, exceto no carregamento inicial
        if (!isFirstUpdate) {
            clearAllHighlights();
        }

        // Sincronização de Peças: Atualiza as imagens de todas as 64 casas
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                squares[row][col].setPiece(piece); // Injeta a ImageView atualizada da peça
            }
        }

        isFirstUpdate = false;

        // Renderização de Destaques de Movimentação (Se houver peça selecionada)
        if (selection.hasSelection()) {
            // Destaca a casa da peça atualmente selecionada pelo usuário
            Position selectSquare = selection.getSelectedPos();
            squares[selectSquare.getRow()][selectSquare.getCol()].getStyleClass().add("selectedSquare");

            // Varre os caminhos calculados e aplica as classes de feedback visual
            for (Position move : selection.getPossibleMoves()) {
                BoardSquare targetSquare = squares[move.getRow()][move.getCol()];

                // Diferencia visualmente caminhos livres de casas contendo peças a serem capturadas
                if (board.getPiece(move) != null) {
                    targetSquare.getStyleClass().add("possible-capture"); // Estilo CSS de ataque (ex: borda vermelha)
                } else {
                    targetSquare.getStyleClass().add("possible-move");    // Estilo CSS de avanço (ex: ponto verde)
                }
            }
        }
        
        // Renderização de Alerta de Xeque: Ilumina a casa do Rei sob ameaça
        if (referee.getCurrentState() instanceof CheckState) {
            Position kingPos = board.findKing(referee.getCurrentTurn());
            squares[kingPos.getRow()][kingPos.getCol()].getStyleClass().add("check-square");
        }

        // Renderização de Fim de Jogo: Aplica o destaque definitivo de Xeque-Mate no Rei derrotado
        if (referee.getCurrentState() instanceof CheckmateState) {
            Position kingPos = board.findKing(referee.getCurrentTurn());
            squares[kingPos.getRow()][kingPos.getCol()].getStyleClass().add("checkmate-square");
        }
    }

    /**
     * Varre todas as casas limpando as classes utilitárias de estilo CSS do JavaFX.
     * Evita o acúmulo visual ou fantasmas de marcação de turnos anteriores.
     */
    public void clearAllHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].getStyleClass().removeAll(
                    "selectedSquare", 
                    "possible-move", 
                    "possible-capture", 
                    "check-square",
                    "checkmate-square"
                );
            }
        }
    }

    /**
     * Ponto de entrada do padrão Observer.
     * Intercepta a notificação enviada pelo ObserverNotifier e redireciona os dados empacotados para a atualização de tela da View.
     */
    @Override
    public void onBoardChanged(Board board, SelectionManager selection, Referee referee) {
        updateFromBoard(board, referee, selection);
    }
}