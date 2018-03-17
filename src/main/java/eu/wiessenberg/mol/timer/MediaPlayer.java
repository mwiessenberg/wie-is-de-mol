package eu.wiessenberg.mol.timer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;

class MediaPlayer {
    private Clip clip;

    void stopAllLoops() {
        if (clip != null && clip.isOpen()) {
            clip.close();
        }
    }

    void playLoop(File file) {
        System.out.println("Playing: " + file.getAbsolutePath());
        try {
            clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception exc) {
            exc.printStackTrace(System.err);
        }
    }
}
