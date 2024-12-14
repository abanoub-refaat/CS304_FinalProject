public class Timer {
    private int timeRemaining;
    private long startTime;
    public void startTimer() {
        this.timeRemaining = 100;
        this.startTime = System.currentTimeMillis();
    }
    public void updateTimer() {
        if (timeRemaining > 0) {
            long currentTime = System.currentTimeMillis();
            int elapsedSeconds = (int) ((currentTime - startTime) / 1000);
            timeRemaining = Math.max(0, 100 - elapsedSeconds);
        }
    }
    public void resetTimer() {
        timeRemaining = 100;
        startTime = 0;
    }
    public int getTimeRemaining() {
        return timeRemaining;
    }
}
