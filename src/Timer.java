public class Timer {
    private int timeRemaining;
    private long startTime;
    public void startTimer() {
        this.timeRemaining = 60;
        this.startTime = System.currentTimeMillis();
    }
    public void updateTimer() {
        if (timeRemaining > 0) {
            long currentTime = System.currentTimeMillis();
            int elapsedSeconds = (int) ((currentTime - startTime) / 1000);
            timeRemaining = Math.max(0, 60 - elapsedSeconds);
        }
    }
    public void resetTimer() {
        timeRemaining = 60;
        startTime = 0;
    }
    public int getTimeRemaining() {
        return timeRemaining;
    }
}
