package com.pchess.controller.interaction;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Position;

/**
 * Gerencia o estado de seleção de peças no tabuleiro.
 * Controla qual casa está atualmente selecionada pelo usuário e armazena a lista de movimentos possíveis calculados para a peça selecionada.
 */
public class SelectionManager {
    // Armazena a posição da peça atualmente selecionada (null se nenhuma estiver selecionada)
    private Position selectedPosition = null;

    private Position promotionPos;
    
    // Lista contendo os caminhos e destinos válidos para a peça selecionada
    private List<Position> possibleMoves = new ArrayList<>();

    /**
     * Verifica se existe alguma peça selecionada no momento.
     */
    public boolean hasSelection() {
        return selectedPosition != null;
    }

    /**
     * Define a nova posição selecionada e armazena seus movimentos possíveis.
     */
    public void select(Position position, List<Position> moves) {
        this.selectedPosition = position;
        this.possibleMoves = moves;
    }

    /**
     * Limpa completamente o estado de seleção e esvazia a lista de movimentos.
     */
    public void clearSelection() {
        this.selectedPosition = null;
        this.possibleMoves.clear();
    }

    // =========================================================================
    // GETTERS
    // =========================================================================

    /**
     * Retorna a posição da casa atualmente selecionada.
     */
    public Position getSelectedPos() {
        return selectedPosition; 
    }

    /**
     * Retorna a lista de movimentos possíveis da peça selecionada para renderização de destaques.
     */
    public List<Position> getPossibleMoves() { 
        return possibleMoves;
    }

    /**
     * Define a posição do peão que está aguardando promoção, indicando que o próximo clique deve ser tratado como escolha de peça para promoção.
     */
    public void setPromotionPending(Position pos) {
        this.promotionPos = pos;
    }

    /**
     * Verifica se há um peão aguardando promoção, o que indica que o próximo clique deve ser tratado como escolha de peça para promoção.
     */
    public boolean isPromotionPending() {
        return promotionPos != null;
    }

    /**
     * Retorna a posição do peão que está aguardando promoção, ou null se não houver promoção pendente.
     */
    public Position getPromotionPos() {
        return promotionPos;
    }

    /**
     * Limpa o estado de promoção pendente, indicando que a escolha de peça para promoção foi concluída ou cancelada.
     */
    public void clearPromotion() {
        this.promotionPos = null;
    }
}