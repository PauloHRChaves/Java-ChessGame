package com.pchess.model.pieces.flyweight;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ImageCache {
    // O cache é um mapa que associa o caminho da imagem (String) ao objeto Image correspondente.
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getImage(String path) {
        return cache.computeIfAbsent(path, k -> new Image(path));
        //? computeIfAbsent: Busca dentro do mapa para ver se já existe uma Image guardada com aquele path.
        //? Se existir, ele ignora o resto da linha e entrega a imagem na hora.
        //? Se não existir, executa a função lambda (k -> new Image(path)), que cria uma nova Image a partir do caminho, armazena essa nova Image no mapa associada ao caminho e retorna a nova Image.
    }
}