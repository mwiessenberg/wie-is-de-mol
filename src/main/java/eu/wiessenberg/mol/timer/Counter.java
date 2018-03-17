package eu.wiessenberg.mol.timer;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Counter {
    private long timeInSeconds = 0;
    private List<CounterListener> listeners = new ArrayList<>();
    private List<Long> triggers = new ArrayList<>();

    private Timer timer = new Timer(false);

    Counter(long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    void addListener(CounterListener listener) {
        listeners.add(listener);
    }

    void setTime(long timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    void start() {
        timer.schedule(new CounterTask(), 1000, 1000);
    }

    void stop() {
        timer.cancel();
    }

    void addTimeTrigger(long i) {
        triggers.add(i);
    }

    interface CounterListener {
        void onCountedDownToZero();
        void onTimeChanged(long secondsRemaining);
        void onTimeTrigger(long secondsRemaining);
    }

    private class CounterTask extends TimerTask {
        @Override
        public void run() {
            timeInSeconds--;
            for(CounterListener listener: listeners) {
                Platform.runLater(() -> listener.onTimeChanged(timeInSeconds));

                if (triggers.contains(timeInSeconds)) {
                    Platform.runLater(() -> listener.onTimeTrigger(timeInSeconds));
                }
            }

            if (timeInSeconds <= 0) {
                for(CounterListener listener: listeners) {
                    Platform.runLater(listener::onCountedDownToZero);

                }
                stop();
            }
        }
    }
}
