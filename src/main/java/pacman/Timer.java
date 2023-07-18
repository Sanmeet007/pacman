package pacman;

public class Timer extends Thread {
    private volatile boolean shouldRun = false;
    private volatile boolean stopTimer = false;
    final private TimerBox label;

    public volatile Integer time = 0;

    public Timer(TimerBox label) {
        this.label = label;
    }

    @Override
    public void run() {
        try {
            while (!stopTimer) {
                if (shouldRun) {
                    time += 1;
                    label.updateTime(time);
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public boolean isPaused() {
        return shouldRun;
    }

    public void startTimer() {
        shouldRun = true;
        time = 0;
    }

    public void pauseTimer() {
        shouldRun = false;
    }

    public void resumeTimer() {
        shouldRun = true;
    }

    public void resetTimer() {
        this.label.setText("0:00");
        shouldRun = false;
        time = 0;
    }

    public void stopTimer() {
        stopTimer = true;
        this.label.setText("0:00");
    }
}