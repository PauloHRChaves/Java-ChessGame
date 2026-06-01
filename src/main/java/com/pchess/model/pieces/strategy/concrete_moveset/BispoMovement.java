package com.pchess.model.pieces.strategy.concrete_moveset;

import java.util.ArrayList;
import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.Piece;
import com.pchess.model.pieces.strategy.MovementStrategy;

/**
 * Estratégia de movimentação específica para o Bispo (Padrão de Projeto STRATEGY).
 * Implementa o cálculo geométrico de caminhos em linhas diagonais contínuas, interrompendo a varredura ao colidir com as bordas do tabuleiro ou outras peças.
 */
public class BispoMovement implements MovementStrategy {

    /**
     * Valida de forma pontual se a casa de destino está contida na lista de movimentos possíveis.
     */
    @Override
    public boolean canMove(Position from, Position to, Board board, Piece piece) {
        List<Position> moves = getPossibleMoves(from, board, piece);
        return moves.contains(to); 
    }

    /**
     * Atua como o radar analítico da peça a partir de sua coordenada atual.
     * Varre as direções geométricas permitidas para a peça, mapeando todas as casas vazias do caminho e aplicando as regras de colisão do xadrez.
     * Permite a captura caso encontre uma peça inimiga e interrompe a varredura imediatamente ao colidir com qualquer obstáculo (aliado ou adversário).
     * @param currentPos Coordenada de origem onde a peça está posicionada.
     * @param board      Instância do tabuleiro para consulta de casas e peças.
     * @param piece      A própria peça que está executando o movimento (para checagem de cor).
     * @return Uma lista com todas as posições geométricas válidas de destino.
     */
    @Override
    public List<Position> getPossibleMoves(Position currentPos, Board board, Piece piece) {
        List<Position> moves = new ArrayList<>();

        int[] directions = {-1, 1};

        int startRow = currentPos.getRow();
        int startCol = currentPos.getCol();

        for (int dr : directions) {
            for (int dc : directions) {
                int r = startRow + dr;
                int c = startCol + dc;

                Position nextPos = new Position(r, c);

                while (board.isValidPosition(nextPos)) {
                    Piece target = board.getPiece(nextPos);

                    if (target == null) {
                        moves.add(nextPos);
                    } else {
                        if (!target.getColor().equals(piece.getColor())) {
                            moves.add(nextPos);
                        }
                        break; 
                    }

                    r += dr;
                    c += dc;
                    nextPos = new Position(r, c); 
                }
            }
        }
        return moves;
    }
}