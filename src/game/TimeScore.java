package game;

import java.util.concurrent.TimeUnit;

public class TimeScore extends Score {
    public TimeScore (Difficulty difficulty, long timeInMilliseconds) {
        super(difficulty, timeInMilliseconds);
    }
    public String getTimeAsString() {
        int seconds = (int) TimeUnit.SECONDS.convert(this.getValue(), TimeUnit.MILLISECONDS);
        return seconds / 60 + "m " + seconds % 60 + "s";
    }

    @Override
    public String toString() {
        return String.format("%12s%12s",
                getTimeAsString(),
                getDifficulty()
        );
    }

    @Override
    public int compareTo(Score o) {
        TimeScore other = (TimeScore) o;

        if (this.difficulty != other.difficulty) {
            return this.difficulty.ordinal() > other.difficulty.ordinal()
                    ? -1
                    : 1;
        }

        if (this.getValue() != other.getValue()) {
            return this.value < other.value
                    ? -1
                    : 1;
        }

        return 0;
    }
}
