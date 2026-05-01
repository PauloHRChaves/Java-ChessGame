package com.pchess.model.pieces;

import com.pchess.model.pieces.flyweight.ImageCache;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Superclasse (Pai) abstrata que representa uma peça de xadrez genérica. 
public abstract class Piece {
    private final String color;
    private final String type;

    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
    }

    // Classe ImagemView é um componente visual do JavaFX.
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