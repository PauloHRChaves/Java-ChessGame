package com.pchess.model.observer;

import com.pchess.controller.interaction.SelectionManager;
import com.pchess.model.board.Board;

public interface GameObserver {
    void onBoardChanged(Board board, SelectionManager selection);
}