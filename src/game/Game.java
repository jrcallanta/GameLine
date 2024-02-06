package game;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class Game {
    protected Difficulty difficulty;
    public enum InstructionDepth {
        FULL, SHORT, TURN
    }

    public Game() {
        this.printInstructions();
        this.selectDifficulty();
    }

    protected void countDown() {
        try {
            for (int i = 0; i < 3; i++) {
                //System.out.println(3 - i + "...");
                switch (i) {
                    case 0 -> System.out.println("READY...");
                    case 1 -> System.out.println("SET...");
                    case 2 -> System.out.println("GO!!!\n");
                    default -> {}
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            // do nothing
        }
    }
    abstract public Score play();
    abstract public void reset();
    abstract public String getGameName();

    abstract public void printInstructions(InstructionDepth depth);

    public void printInstructions() {
        printInstructions(InstructionDepth.FULL);
    }
    public void selectDifficulty() {
        Scanner scanner = new Scanner(System.in);

        question: while (true) {
            System.out.println();
            System.out.println("CHOOSE DIFFICULTY:");
            System.out.println("E) EASY");
            System.out.println("M) MEDIUM");
            System.out.println("H) HARD");
            System.out.println();
            System.out.print("> ");

            switch (scanner.nextLine().toLowerCase().trim()) {
                case "e" -> {
                    this.difficulty = Difficulty.EASY;
                    break question;
                }
                case "m" -> {
                    this.difficulty = Difficulty.MEDIUM;
                    break question;
                }
                case "h" -> {
                    this.difficulty = Difficulty.HARD;
                    break question;
                }
                case "quit" -> {
                    this.difficulty = null;
                    break question;
                }
                default -> {}
            }
        }
        System.out.println();
    }

    public String askPlayAgain() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("PLAY AGAIN?");
            System.out.println();
            System.out.println("Y) YES");
            System.out.println("N) NO");
            System.out.println("D) CHANGE DIFFICULTY");
            System.out.println();
            System.out.print("> ");

            switch (scanner.nextLine().toLowerCase().trim()) {
                case "y", "yes" -> {
                    return "YES";
                }
                case "n", "no", "quit" -> {
                    return "NO";
                }
                case "d" -> {
                    return "CHANGE_DIFFICULTY";
                }
                default -> {}
            }
        }
    }
}
