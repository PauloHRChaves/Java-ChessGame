package com.pchess.model.observer;

import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;
import com.pchess.model.game.Referee;

public interface GameObserver {
    void onBoardChanged(Board board, SelectionManager selection, Referee referee);
}