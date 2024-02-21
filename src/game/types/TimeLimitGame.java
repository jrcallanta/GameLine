package game.types;

import game.Game;
import game.util.ConsoleInput;

import java.util.Date;
import java.util.concurrent.TimeUnit;

abstract public class TimeLimitGame extends Game {
    private Date startTime;
    private long timeLimit;
    final private ConsoleInput consoleInput;

    public TimeLimitGame () {
        this.timeLimit = TimeUnit.MILLISECONDS.convert(15, TimeUnit.SECONDS);
        this.consoleInput = new ConsoleInput();
    }

    @Override
    public void reset() {
        this.startTime = null;
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

    public void setTimeLimit(int time, TimeUnit timeUnit) {
        this.timeLimit = TimeUnit.MILLISECONDS.convert(time, timeUnit);
    }
}
