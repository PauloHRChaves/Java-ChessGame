package com.pchess.model.pieces;

import java.util.List;

import com.pchess.model.board.Board;
import com.pchess.model.board.Position;
import com.pchess.model.pieces.flyweight.ImageCache;
import com.pchess.model.pieces.strategy.MovementStrategy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Piece {
    private final String color;
    private final String type;
    
    private boolean moved = false;
    protected MovementStrategy movementStrategy;

    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getType() { return type; }

    public String getColor() { return color; }

    public boolean hasMoved() { return moved; }

    public void setMoved() { this.moved = true; }

    public boolean canMove(Position from, Position to, Board board) {
        // Agora 'from' e 'to' existem, pois foram declarados ali em cima no parâmetro
        return movementStrategy.canMove(from, to, board, this);
    }

    public List<Position> getPossibleMoves(Position currentPos, Board board) {
        // Agora 'currentPos' existe, pois foi declarado no parâmetro
        return movementStrategy.getPossibleMoves(currentPos, board, this);
    }

    public ImageView getView() {
        String path = "/images/" + color + "_" + type + ".png";

        Image image = ImageCache.getImage(getClass().getResource(path).toExternalForm());
        //? É o arquivo .png carregado na memória, mas ainda não é algo que pode ser exibido na tela.
        
        ImageView view = new ImageView(image);
        //? É um componente visual do JavaFX que pode ser adicionado à interface gráfica. Ele é criado a partir do Image, e é o que realmente aparece na tela.

        view.setFitWidth(100);
        view.setPreserveRatio(true);

        return view;
    }
}