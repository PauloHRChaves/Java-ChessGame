package com.pchess.utils;

import javafx.scene.media.AudioClip;

public class SoundManager {
    private static final AudioClip MOVE_SOUND = new AudioClip(SoundManager.class.getResource("/sounds/move.wav").toExternalForm());
    private static final AudioClip CAPTURE_SOUND = new AudioClip(SoundManager.class.getResource("/sounds/capture.wav").toExternalForm());
    private static final AudioClip CHECK_SOUND = new AudioClip(SoundManager.class.getResource("/sounds/check.wav").toExternalForm());
    private static final AudioClip CHECKMATE_SOUND = new AudioClip(SoundManager.class.getResource("/sounds/checkmate.wav").toExternalForm());

    /**
     * Construtor privado para impedir a instanciação da classe.
     * Garante que a SoundManager seja utilizada estritamente como uma classe utilitária.
     */
    private SoundManager() {}

    /**
     * Reproduz o efeito sonoro padrão para deslocamentos em casas vazias.
     */
    public static void playMoveSound() {
        MOVE_SOUND.play();
    }

    /**
     * Reproduz o efeito sonoro específico para lances que resultam em captura de peças.
     */
    public static void playCaptureSound() {
        CAPTURE_SOUND.play();
    }

    /**
     * Reproduz o efeito sonoro de alerta para lances que colocam o Rei adversário em Xeque.
     */
    public static void playCheckSound() {
        CHECK_SOUND.play();
    }

    /**
     * Reproduz o efeito sonoro de encerramento para lances que resultam em Xeque-Mate.
     */
    public static void playCheckmateSound() {
        CHECKMATE_SOUND.play();
    }
}