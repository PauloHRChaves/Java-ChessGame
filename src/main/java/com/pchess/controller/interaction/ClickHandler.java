package com.pchess.controller.interaction;

import java.util.List;

import com.pchess.controller.Orchestrator;
import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.state.Referee;
import com.pchess.utils.SoundManager;

public class ClickHandler {
    private final Board board;
    private final Referee referee;
    private final SelectionManager selection;
    private final Orchestrator orquestrator;

    public ClickHandler(Board board, Referee referee, SelectionManager selection, Orchestrator orquestrator) {
        this.board = board;
        this.referee = referee;
        this.selection = selection;
        this.orquestrator = orquestrator;
    }

    public void handleClick(int row, int col) {
        Position clickedPos = new Position(row, col);

        if (!selection.hasSelection()) {
            handleFirstClick(clickedPos);
        } else {
            handleSecondClick(clickedPos);
        }
    }

    private void handleFirstClick(Position position) {
        Piece piece = board.getPiece(position); 
        
        if (referee.canSelect(piece)) {
            List<Position> moves = piece.getPossibleMoves(position, board);
            selection.select(position, moves);
            orquestrator.notifyBoardChanged();
        }
    }

    private void handleSecondClick(Position clickedPos) {
        Position selectedPos = selection.getSelectedPos();

        // cancelou seleção
        if (selectedPos.equals(clickedPos)) {
            selection.clearSelection();
            orquestrator.notifyBoardChanged();
            return;
        }

        Piece pieceToMove = board.getPiece(selectedPos);

        boolean validMove = referee.isValidMove(pieceToMove, selectedPos, clickedPos, board);

        if (validMove) {
            // VERIFICA CAPTURA
            Piece targetPiece = board.getPiece(clickedPos);

            boolean isCapture = targetPiece != null;

            // MOVE
            board.movePiece(selectedPos, clickedPos);
            pieceToMove.setMoved();

            // SOM
            if (isCapture) {
                SoundManager.playCaptureSound();
            } else {
                SoundManager.playMoveSound();
            }

            referee.nextTurn();
            selection.clearSelection();

        } else {
            handleFirstClick(clickedPos);
        }
        orquestrator.notifyBoardChanged();
    }
}