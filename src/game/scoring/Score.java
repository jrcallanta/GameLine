package game.scoring;

import game.Difficulty;

public class Score implements Comparable<Score> {

    protected Difficulty difficulty;
    protected long value;

    public Score(Difficulty difficulty, long value) {
        this.difficulty = difficulty;
        this.value = value;
    }
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public long getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("%12s%12s",
                getValue(),
                getDifficulty()
        );
    }

    @Override
    public int compareTo(Score other) {
        if (this.difficulty != other.difficulty) {
            return this.difficulty.ordinal() > other.difficulty.ordinal()
                ? -1
                : 1;
        }

        if (this.getValue() != other.getValue()) {
            return this.value > other.value
                ? -1
                : 1;
        }

        return 0;
    }
}
