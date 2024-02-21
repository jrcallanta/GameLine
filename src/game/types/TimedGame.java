package game.types;

import game.Game;
import game.scoring.Score;

import java.util.Date;

abstract public class TimedGame extends Game {
    private Date startTime;
    private Date endTime;

    public TimedGame () {
        super();
    }

    @Override
    public void reset() {
        this.startTime = null;
        this.endTime = null;
    }

    @Override
    public Score quit() {
        this.startTime = null;
        this.endTime = null;
        return super.quit();
    }

    public void startTimer() {
        this.startTime = new Date();
    }

    public void stopTimer() {
        this.endTime = new Date();
    }

    public long getStartTime() {
        return startTime.getTime();
    }

    public long getEndTime() {
        return endTime.getTime();
    }

    public long getElapsedTime() {
        return this.endTime.getTime() - this.startTime.getTime();
    }

}
