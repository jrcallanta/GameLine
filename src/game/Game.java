package game;

import menu.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Game {
    protected Difficulty difficulty;

    public enum InstructionDepth {
        FULL, SHORT, TURN
    }
    final private Menu playAgain;
    final private Menu difficultyChange;

    public Game() {
        this.printInstructions();

        this.difficultyChange = new Menu();
        this.difficultyChange.setPrompt("CHOOSE DIFFICULTY");
        this.difficultyChange.addOption("EASY", "E", Arrays.asList("e", "easy"));
        this.difficultyChange.addOption("MEDIUM", "M", Arrays.asList("m", "medium"));
        this.difficultyChange.addOption("HARD", "H", Arrays.asList("h", "hard"));
        this.difficultyChange.addSecretOption("QUIT", List.of("quit"));

        this.playAgain = new Menu();
        this.playAgain.setPrompt("PLAY AGAIN?");
        this.playAgain.addOption("YES", "Y", Arrays.asList("y", "yes"));
        this.playAgain.addOption("NO", "N", Arrays.asList("n", "no"));
        this.playAgain.addOption("CHANGE_DIFFICULTY", "D", Arrays.asList("d"));
        this.playAgain.addSecretOption("QUIT", Arrays.asList("quit"));
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
    public Difficulty getDifficulty() {
        return this.difficulty;
    }
    public Difficulty selectDifficulty() {
        switch (this.difficultyChange.ask()) {
            case "EASY" ->
                this.difficulty = Difficulty.EASY;
            case "MEDIUM" ->
                this.difficulty = Difficulty.MEDIUM;
            case "HARD" ->
                this.difficulty = Difficulty.HARD;
            case "QUIT" ->
                this.difficulty = null;
            default -> {}
        }
        return this.difficulty;
    }

    public boolean askPlayAgain() {
        switch (this.playAgain.ask()) {
            case "YES" -> { return true; }
            case "NO", "QUIT" -> { return false; }
            case "CHANGE_DIFFICULTY" -> { return this.selectDifficulty() != null; }
            default -> { }
        }

        return false;
    }
    public Score quit() {
        System.out.println(">> QUITTING");
        return null;
    }
}
