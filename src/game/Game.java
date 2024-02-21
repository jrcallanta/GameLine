package game;

import game.scoring.Score;
import menu.Menu;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public abstract class Game {
    protected Difficulty difficulty;
    protected Scanner scanner;
    final private Menu playAgain;
    final protected Menu changeDifficulty;
    public enum InstructionDepth { FULL, SHORT, TURN }

    public Game() {
        this.printInstructions();

        this.changeDifficulty = new Menu();
        this.changeDifficulty.setPrompt("CHOOSE DIFFICULTY");
        this.changeDifficulty.addOption("EASY", "E", Arrays.asList("e", "easy"));
        this.changeDifficulty.addOption("MEDIUM", "M", Arrays.asList("m", "medium"));
        this.changeDifficulty.addOption("HARD", "H", Arrays.asList("h", "hard"));
        this.changeDifficulty.addSecretOption("QUIT", List.of("quit"));

        this.playAgain = new Menu();
        this.playAgain.setPrompt("PLAY AGAIN?");
        this.playAgain.setBorderChar("-");
        this.playAgain.addOption("YES", "Y", Arrays.asList("y", "yes"));
        this.playAgain.addOption("NO", "N", Arrays.asList("n", "no"));
        this.playAgain.addOption("CHANGE DIFFICULTY", "D", Arrays.asList("d"));
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
    abstract public void reset();
    abstract public Score play();
    abstract public String getGameName();
    abstract public void printInstructions(InstructionDepth depth);

    public void printInstructions() {
        printInstructions(InstructionDepth.FULL);
    }
    public Difficulty getDifficulty() {
        return this.difficulty;
    }
    public Difficulty selectDifficulty() throws IOException {
        if (this.scanner == null) throw new IOException("NoScannerProvided");

        switch (this.changeDifficulty.ask(this.scanner)) {
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

    public boolean askPlayAgain() throws IOException {
        if (this.scanner == null) throw new IOException("NoScannerProvided");

        switch (this.playAgain.ask(this.scanner)) {
            case "YES" -> { return true; }
            case "NO", "QUIT" -> { return false; }
            case "CHANGE DIFFICULTY" -> { return this.selectDifficulty() != null; }
            default -> { }
        }

        return false;
    }
    public Score quit() {
        System.out.println(">> QUITTING");
        return null;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
