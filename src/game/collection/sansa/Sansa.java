package game.collection.sansa;

import game.types.TimeLimitGame;
import game.scoring.Score;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Sansa extends TimeLimitGame {
    final Random random;
    private int points;


    public Sansa() {
        super(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS));
        this.random = new Random();
    }

    public Score play() {
        //Scanner scanner = new Scanner(System.in);

        this.countDown();
        this.startTimer();
        System.out.println("Keep pressing Enter to earn points...");

        while (this.getRemainingTime() > 0) {
            if (this.timedReadLine() != null)
            {
                System.out.println("+1000");
                points += 1000;
            }
        }
        return new Score(this.difficulty, points);
    }

    @Override
    public String getGameName() {
        return "SANSA";
    }

    @Override
    public void printInstructions(InstructionDepth depth) {

    }
}
