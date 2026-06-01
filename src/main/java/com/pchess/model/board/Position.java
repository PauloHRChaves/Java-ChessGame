package com.pchess.model.board;

import java.util.Objects;

/**
 * Representa uma coordenada imutável de uma casa no tabuleiro de xadrez.
 */
public class Position {
    private final int row;
    private final int col;

    /**
     * Construtor da Posição.
     * Define as coordenadas de matriz permanentes da casa ao instanciar o objeto.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================

    /**
     * Retorna o índice numérico da linha (0 a 7).
     */
    public int getRow() { 
        return row; 
    }

    /**
     * Retorna o índice numérico da coluna (0 a 7).
     */
    public int getCol() { 
        return col; 
    }

    // =========================================================================
    // CONTRATO AUXILIAR (Comparação e Coleções)
    // =========================================================================

    /**
     * Compara se este objeto de posição possui os mesmos valores internos de outro.
     * Necessário para métodos de busca em listas como 'moves.contains(clickedPos)'.
     */
    @Override
    public boolean equals(Object object) {
        // Se referenciam o mesmo endereço de memória, são idênticos
        if (this == object) return true;
        
        // Se o objeto comparado for nulo ou de outra classe, não são iguais
        if (object == null || getClass() != object.getClass()) return false;

        Position position = (Position) object;

        // Regra de igualdade estrutural: mesma linha E mesma coluna
        return row == position.row && col == position.col;
    }

    /**
     * Gera um código de hash numérico exclusivo baseado nos valores combinados de row e col.
     * Essencial para otimização em coleções do tipo HashSet ou HashMap.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}