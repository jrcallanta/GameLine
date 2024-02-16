package game.scoring;

import game.Difficulty;

public class ComboScore extends Score {
    protected long timeInMilliseconds;
    public ComboScore(Difficulty difficulty, long timeInMilliseconds, int value) {
        super(difficulty, timeInMilliseconds);
        this.difficulty = difficulty;
        this.timeInMilliseconds = timeInMilliseconds;
        this.value = timeInMilliseconds;
    }

    public long getTime() {
        return this.timeInMilliseconds;
    }
}
