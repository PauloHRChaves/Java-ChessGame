package com.pchess.model.board;

import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.PieceFactory;

/**
 * Representa o tabuleiro físico de xadrez na memória.
 * Gerencia uma matriz quadrada de 8x8 posições, controlando a alocação, movimentação, validação de limites e busca de peças específicas.
 */
public class Board {
    // Matriz bidimensional que armazena o estado das 64 casas do tabuleiro
    private final Piece[][] pieces = new Piece[8][8];

    /**
     * Construtor do Tabuleiro.
     * Aciona automaticamente a configuração e posicionamento inicial das peças.
     */
    public Board() {
        setupInitialPieces();
    }

    /**
     * Configura o estado inicial padrão de um tabuleiro de xadrez.
     */
    private void setupInitialPieces() {
        // Ordem das peças na primeira e última linha
        String[] rowOrder = {"torre", "cavalo", "bispo", "rainha", "rei", "bispo", "cavalo", "torre"};

        for (int col = 0; col < 8; col++) {
            // --- PEÇAS PRETAS (Linhas 0 e 1) ---
            pieces[0][col] = PieceFactory.createPiece(rowOrder[col], "black");
            pieces[1][col] = PieceFactory.createPiece("peao", "black");
            
            // --- PEÇAS BRANCAS (Linhas 6 e 7) ---
            pieces[6][col] = PieceFactory.createPiece("peao", "white");
            pieces[7][col] = PieceFactory.createPiece(rowOrder[col], "white");
        }
    }

    // =========================================================================
    // MÉTODOS DE CONSULTA
    // =========================================================================

    /**
     * Retorna a peça contida em uma coordenada numérica específica.
     * Retorna null caso a posição esteja vazia ou fora dos limites.
     */
    public Piece getPiece(int row, int col) {
        if (!isValidPosition(row, col)) {
            return null;
        }
        return pieces[row][col];
    }

    /**
     * Sobrecarga para retornar a peça com base em um objeto Position.
     */
    public Piece getPiece(Position position) {
        if (position == null || !isValidPosition(position)) {
            return null;
        }
        return pieces[position.getRow()][position.getCol()];
    }

    // =========================================================================
    // MÉTODOS DE VALIDAÇÃO
    // =========================================================================

    /**
     * Verifica se os índices de matriz informados pertencem ao intervalo 0-7 do tabuleiro.
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    /**
     * Sobrecarga que valida os limites do tabuleiro usando um objeto Position.
     */
    public boolean isValidPosition(Position position) {
        if (position == null) return false;
        return isValidPosition(position.getRow(), position.getCol());
    }

    // =========================================================================
    // MÉTODOS DE ALTERAÇÃO E MOVIMENTAÇÃO
    // =========================================================================

    /**
     * Aloca à força uma peça em uma posição específica do tabuleiro.
     */
    public void setPiece(Position position, Piece piece) {
        if (position != null && isValidPosition(position)) {
            pieces[position.getRow()][position.getCol()] = piece;
        }
    }

    /**
     * Executa a transposição de uma peça da casa de origem para a casa de destino.
     * Substitui o destino e limpa a casa antiga (atribui null).
     */
    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        setPiece(to, piece);
        setPiece(from, null);
    }

    // =========================================================================
    // MÉTODOS UTILITÁRIOS
    // =========================================================================

    /**
     * Varre a matriz procurando a posição do Rei da cor informada.
     * Usado pelo Referee para calcular situações de Xeque e Xeque-Mate.
     */
    public Position findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position position = new Position(row, col);
                Piece p = getPiece(position);
                
                // Valida se a peça encontrada é o Rei da cor especificada
                if (p != null && p.getType().equalsIgnoreCase("Rei") && p.getColor().equals(color)) {
                    return position;
                }
            }
        }
        return null;
    }

    //! Debug
    public String toCoords(Position position) {
        char column = (char) (position.getCol() + 'A');
        int line = 8 - position.getRow();
        return "" + column + line;
    }
}