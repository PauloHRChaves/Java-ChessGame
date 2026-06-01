package com.pchess.model.game.state;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.game.GameState;
import com.pchess.model.game.Referee;
import com.pchess.model.pieces.Piece;

public class CheckmateState implements GameState {

    @Override
    public boolean canSelect(Piece piece, String currentTurn) {
        // Ninguém pode selecionar nada. O jogo acabou.
        return false;
    }

    @Override
    public boolean isValidMove(Piece piece, Position from, Position to, Board board, Referee referee) {
        // Nenhum movimento é mais permitido.
        return false;
    }
}