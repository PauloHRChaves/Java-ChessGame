package com.pchess.controller.interaction;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Position;

public class SelectionManager {
    private Position selectedPosition = null;
    private List<Position> possibleMoves = new ArrayList<>();

    public boolean hasSelection() {
        return selectedPosition != null;
    }

    public void select(Position position, List<Position> moves) {
        this.selectedPosition = position;
        this.possibleMoves = moves;
    }

    public void clearSelection() {
        this.selectedPosition = null;
        this.possibleMoves.clear();
    }

    public Position getSelectedPos() { return selectedPosition; }
    public List<Position> getPossibleMoves() { return possibleMoves; }
}