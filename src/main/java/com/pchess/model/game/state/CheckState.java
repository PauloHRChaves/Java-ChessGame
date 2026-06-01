package com.pchess.model.game.state;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.game.Referee;
import com.pchess.model.pieces.Piece;

/**
 * Representa o estado do turno em que o Rei do jogador atual está sofrendo Xeque.
 * Herda diretamente de NormalState, reutilizando por herança a lógica de simulação e validação de movimentos com segurança do Rei.
 */
public class CheckState extends NormalState {

    /**
     * Valida se uma peça pode ser selecionada durante uma situação de Xeque.
     * Reutiliza a regra da classe pai (NormalState), permitindo tocar em qualquer peça própria.
     */
    @Override
    public boolean canSelect(Piece piece, String currentTurn) {
        return super.canSelect(piece, currentTurn);
    }

    /**
     * Valida se o movimento é capaz de salvar o Rei do Xeque atual.
     * Reutiliza o mecanismo de simulação da classe pai (super.isValidMove).
     * Como a classe pai testa se o Rei fica seguro, qualquer movimento que não bloqueie a ameaça, não capture o atacante ou não mova o Rei para longe será bloqueado automaticamente aqui.
     */
    @Override
    public boolean isValidMove(Piece piece, Position from, Position to, Board board, Referee referee) {
        return super.isValidMove(piece, from, to, board, referee);
    }
}