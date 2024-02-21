package game.types;

import game.Game;
import game.util.ConsoleInput;

import java.util.Date;

abstract public class TimeLimitGame extends Game {
    private Date startTime;
    private long timeLimit;
    private ConsoleInput consoleInput;

    public TimeLimitGame (long timeLimitInMS) {
        this.timeLimit = timeLimitInMS;
        this.consoleInput = new ConsoleInput();
    }

    @Override
    public void reset() {

    }

    public void startTimer() {
        this.startTime = new Date();
    }

    public long getRemainingTime() {
        long elapsed = new Date().getTime() - this.startTime.getTime();
        return Math.max(0, this.timeLimit - elapsed);
    }

    public String timedReadLine() {
        String line = null;
        try {
            line = this.consoleInput.timedReadLine(this.getRemainingTime());
        } catch (InterruptedException e) {

        }
        return line;
    }
    public long getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(long timeLimitInMS) {
        this.timeLimit = timeLimitInMS;
    }
}
