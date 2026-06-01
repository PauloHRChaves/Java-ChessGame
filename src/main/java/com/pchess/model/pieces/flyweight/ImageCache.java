package com.pchess.model.pieces.flyweight;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Gerenciador de cache estático para recursos visuais (Padrão de Projeto FLYWEIGHT).
 * Otimiza o consumo de memória RAM e processamento de I/O, garantindo que cada imagem de peça (.png) seja carregada do disco apenas uma única vez durante o ciclo de vida do jogo.
 */
public class ImageCache {
    // Mapa de persistência em memória que associa a URL textual do recurso à sua instância Image decodificada
    private static final Map<String, Image> cache = new HashMap<>();

    /**
     * Recupera uma imagem do cache ou a instancia caso seja a primeira requisição do recurso.
     * Utiliza o método atômico 'computeIfAbsent' para garantir busca e inserção seguras.
     * @return A instância compartilhada da classe Image do JavaFX.
     */
    public static Image getImage(String path) {
        // Se a chave (path) existir, retorna o valor imediatamente. 
        // Caso contrário, executa a expressão lambda para criar, salvar no mapa e retornar a nova Image.
        return cache.computeIfAbsent(path, k -> new Image(path));
    }
}