package game.collection.sansa;

import game.scoring.Score;
import game.types.TimeLimitGame;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class Sansa extends TimeLimitGame {
    final Random random;
    private int bitCount;

    public Sansa() {
        super();
        this.random = new Random();

        this.changeDifficulty.addDescriptor(
                "EASY", "[2 bits 20 seconds]");
        this.changeDifficulty.addDescriptor(
                "MEDIUM", "[4 bits 60 seconds]");
        this.changeDifficulty.addDescriptor(
                "HARD", "[8 bits 90 seconds]");
    }

    @Override
    public void reset() {
        super.reset();

        switch (this.difficulty) {
            case EASY -> {
                this.bitCount = 2;
                this.setTimeLimit(20, TimeUnit.SECONDS);
            }
            case MEDIUM -> {
                this.bitCount = 4;
                this.setTimeLimit(60, TimeUnit.SECONDS);
            }
            case HARD -> {
                this.bitCount = 8;
                this.setTimeLimit(90, TimeUnit.SECONDS);
            }
            default -> throw new RuntimeException("NoDifficultySet");
        }
    }

    @Override
    public Score play() {
        int num1 = this.getNextNum();
        int num2 = this.getNextNum();
        int target = num1 ^ num2;
        int points = 0;

        this.countDown();
        this.startTimer();
        String line;
        Scanner lineScanner;

        gameplay: do  {
            System.out.println(num1 + " xor " + num2);

            do {
                System.out.print("  = ");

                line = this.timedReadLine();
                if (line == null)
                    break gameplay;

                lineScanner = new Scanner(line);
                if (lineScanner.hasNext(Pattern.compile("quit|q", Pattern.CASE_INSENSITIVE)))
                    return null;

            } while (!lineScanner.hasNextInt() || lineScanner.nextInt() != target);

            points++;
            num1 = target;
            num2 = this.getNextNum();
            target = num1 ^ num2;
        } while(this.getRemainingTime() > 0);

        System.out.println("TIMES UP!");
        System.out.println("SCORE: " + points);
        return new Score(this.difficulty, points);
    }

    private int getNextNum() {
        return this.random.nextInt((int) Math.pow(2, this.bitCount));
    }
    @Override
    public String getGameName() {
        return "SANSA";
    }

    @Override
    public void printInstructions(InstructionDepth depth) {

    }
}
