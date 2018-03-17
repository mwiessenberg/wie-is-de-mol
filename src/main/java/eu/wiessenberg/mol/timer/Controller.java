package eu.wiessenberg.mol.timer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;

public class Controller implements Counter.CounterListener {
    private static final int MINUTE = 60;

    @FXML private Text timeLeft;
    @FXML private Label tijdVolgendeOpdracht;

    private MediaPlayer mediaPlayer;
    private boolean started = false;

    private final long defaultTime = 900L;
    private long timerInSeconds = defaultTime;
    private Counter counter;

    @FXML
    public void initialize() {
        updateTimeLabel();
        mediaPlayer = new MediaPlayer();
        reset();
    }

    private void updateTimeLabel() {
        updateCountdown(timerInSeconds);
    }

    public void plusMinute() {
        stop();
        timerInSeconds += MINUTE;
        counter.setTime(timerInSeconds);
        updateTimeLabel();
    }

    void minusMinute() {
        stop();
        timerInSeconds -= MINUTE;
        counter.setTime(timerInSeconds);
        updateTimeLabel();
    }

    boolean isStarted() {
        return started;
    }

    void start() {
        if (!started) {
            tijdVolgendeOpdracht.setVisible(true);
            counter = new Counter(timerInSeconds);
            counter.addListener(this);
            counter.addTimeTrigger(10);
            counter.start();
            playLoop();
            started = true;
        }
    }

    void stop() {
        if (counter != null) {
            counter.stop();
            mediaPlayer.stopAllLoops();
            started = false;
        }
    }

    void intro() {
        if (!started) {
            tijdVolgendeOpdracht.setVisible(false);
            timeLeft.setText("Welkom");
            playLoop();
            started = true;
        } else {
            stop();
            reset();
            started = false;
        }
    }

    void reset() {
        stop();
        timerInSeconds = defaultTime;
        counter = new Counter(defaultTime);
        counter.addListener(this);
        counter.addTimeTrigger(10);
        updateTimeLabel();
    }

    private void playLoop() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("mol-tune.wav");
        if (url != null) {
            mediaPlayer.playLoop(new File(url.getFile()));
        } else {
            System.err.println("Tune not found!");
        }
    }

    @Override
    public void onCountedDownToZero() {
        counter.stop();
    }

    @Override
    public void onTimeChanged(long secondsRemaining) {
        updateCountdown(secondsRemaining);
    }

    private void updateCountdown(long secondsRemaining) {
        timeLeft.setText(StringUtils.formatTime(secondsRemaining));
    }

    @Override
    public void onTimeTrigger(long secondsRemaining) {
    }
}
