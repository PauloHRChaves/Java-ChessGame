package com.pchess.model.game;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;

public interface GameState {
    // Define se o jogador da vez pode selecionar aquela peça
    boolean canSelect(Piece piece, String currentTurn);
    
    // Define se o movimento é legal segundo as regras do estado atual
    boolean isValidMove(Piece piece, Position from, Position to, Board board, Referee referee);
}