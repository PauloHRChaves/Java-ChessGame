package com.pchess.utils;

import javafx.scene.media.AudioClip;

public class SoundManager {
    private static final AudioClip MOVE_SOUND = new AudioClip(SoundManager.class.getResource("/sounds/move.wav").toExternalForm());
    private static final AudioClip CAPTURE_SOUND = new AudioClip(SoundManager.class.getResource("/sounds/capture.wav").toExternalForm()    );

    private SoundManager() {
    }

    public static void playMoveSound() {
        MOVE_SOUND.play();
    }

    public static void playCaptureSound() {
        CAPTURE_SOUND.play();
    }
}