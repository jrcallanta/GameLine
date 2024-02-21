package game.collection.matriks;

import game.Game;
import game.TimedGame;
import game.scoring.Score;
import game.scoring.TimeScore;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matriks extends TimedGame {
    private MatriksSquare matriks;
    private int numOfFlips;
    private int numOfTurns;


    @Override
    public void reset() {
        super.reset();

        switch (this.difficulty) {
            case EASY ->
                this.matriks = new MatriksSquare(2);

            case MEDIUM ->
                this.matriks = new MatriksSquare(3);

            case HARD ->
                this.matriks = new MatriksSquare(4);

            default ->
                throw new RuntimeException("NoDifficultySet");
        }
        this.numOfFlips = 0;
        this.numOfTurns = 0;
    }
    @Override
    public Score play() {
        this.printInstructions(InstructionDepth.FULL);
        this.countDown();

        this.matriks.print();
        this.matriks.printDetails();
        this.startTimer();

        System.out.print("FLIP: ");
        String cmd = this.scanner.nextLine();
        Pattern cmdPat = Pattern.compile("(([R|C])([1-" + this.matriks.getMatrixSize() + "])+[ *]?|QUIT|Q)", Pattern.CASE_INSENSITIVE);
        Matcher cmdMat = cmdPat.matcher(cmd);
        while(true) {
            // Find if Valid Cmd Exists
            while (!cmdMat.find()) {
                System.out.println();
                printInstructions(Game.InstructionDepth.SHORT);
                cmd = this.scanner.nextLine();
                cmdMat = cmdPat.matcher(cmd);
            }

            // Check if entire Cmd is Only Valid Cmd's
            StringBuilder cmds = new StringBuilder();
            do cmds.append(cmdMat.group(1));
            while (cmdMat.find());
            if (!cmd.contentEquals(cmds)) {
                printInstructions(Game.InstructionDepth.SHORT);
                cmd = this.scanner.nextLine();
                cmdMat = cmdPat.matcher(cmd);
                continue;
            }

            this.numOfTurns++;
            // Reset Matcher
            cmdMat = cmdPat.matcher(cmd);
            cmdMat.find();
            do {
                switch (cmdMat.group(1).toLowerCase().charAt(0)) {
                    case 'r' -> {
                        String rows = cmdMat.group(1).substring(1).trim();
                        for (int i = 0; i < rows.length(); i++)
                            matriks.flipRow(Integer.parseInt(rows.substring(i, i + 1)) - 1);
                        this.numOfFlips++;
                    }
                    case 'c' -> {
                        String cols = cmdMat.group(1).substring(1).trim();
                        for (int i = 0; i < cols.length(); i++)
                            matriks.flipCol(Integer.parseInt(cols.substring(i, i + 1)) - 1);
                        this.numOfFlips++;
                    }
                    case 'q' -> {
                        return quit();
                    }
                    default -> {
                    }
                }
            } while (cmdMat.find());

            this.matriks.print();
            this.matriks.printDetails();
            System.out.println("FLIPS: " + this.numOfFlips);

            if (!matriks.targetReached()) {
                System.out.print("FLIP: ");
                cmd = this.scanner.nextLine();
                cmdMat = cmdPat.matcher(cmd);
                System.out.println("------------");
                continue;
            }

            this.stopTimer();
            System.out.println();
            this.printResults();
            return new TimeScore(
                    this.difficulty,
                    this.getElapsedTime()
            );
        }
    }

    @Override
    public String getGameName() {
        return "MATRIKS";
    }

    private void printResults() {
        System.out.println("TARGET REACHED!");
        System.out.println("YOU WIN!");
        int seconds = (int) TimeUnit.SECONDS.convert(this.getElapsedTime(), TimeUnit.MILLISECONDS);
        System.out.println("TIME: " + seconds/60 + "m " + seconds%60 + "s");
        System.out.println("FLIPS: " + this.numOfFlips);
    }

    @Override
    public void printInstructions(Game.InstructionDepth depth) {
        switch (depth) {
            case FULL -> {
                System.out.println();
                System.out.println("============================================");
                System.out.println("[GOAL]: The matrix board has 2N * 2N elements.");
                System.out.println("  A corner sum is calculated by adding all the");
                System.out.println("  elements in the first N rows and N columns.");
                System.out.println("  This can also be visualized as the top left");
                System.out.println("  N x N corner of the matrix board. The goal is");
                System.out.println("  to manipulate the matrix's rows and columns");
                System.out.println("  to reach the max possible corner sum. This");
                System.out.println("  target sum is shown on each turn.");
                System.out.println();
                System.out.println("[INSTRUCTIONS]: The player can flip any");
                System.out.println("  row or column any number of times.");
                System.out.println();
                System.out.println("  To flip ROWs, type 'r' followed by the");
                System.out.println("  row number(s).");
                System.out.println("  To flip COLs, type 'c' followed by the");
                System.out.println("  column number(s).");
                System.out.println();
                System.out.println("  ex.   r123      ->  flip rows 1, 2, and 3");
                System.out.println("        c24       ->  flip cols 2 and 4");
                System.out.println("        r2c34r2   ->  flip row 2 then");
                System.out.println("                      flip col 3 and 4 then");
                System.out.println("                      flip row 2");
                System.out.println();
                System.out.println("============================================");
                System.out.println();
            }

            case SHORT -> {
                System.out.println("[INSTRUCTIONS]: The player can flip any");
                System.out.println("  row or column any number of times. A flip");
                System.out.println("  is entered in the following format: ");
                System.out.println();
                System.out.println("  ROW r1 r2 r3... ");
                System.out.println("  or ");
                System.out.println("  COL c1 c2 c3... ");
                System.out.println();
            }

            default -> {}
        }
    }
}
