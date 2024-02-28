package game.collection.matriks;

import game.GameInformation;
import game.scoring.Score;
import game.scoring.TimeScore;
import game.types.TimedGame;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matriks extends TimedGame {
    private MatriksSquare matriks;
    private int numOfFlips;
    private int numOfTurns;

    public Matriks () {
        super();

        this.changeDifficulty.addDescriptor(
                "EASY", "[4 x 4]");
        this.changeDifficulty.addDescriptor(
                "MEDIUM", "[6 x 6]");
        this.changeDifficulty.addDescriptor(
                "HARD", "[8 x 8]");

        this.gameInformation = new GameInformation(
                "[GOAL]:\n\n" +
                        "The matrix board has 2Nx2N elements." +
                        " A corner sum is calculated by adding all the" +
                        " elements in the first N rows and N columns." +
                        " This can also be visualized as the top left" +
                        " NxN corner of the matrix board. The goal is" +
                        " to manipulate the matrix's rows and columns" +
                        " to reach the max possible corner sum. This" +
                        " target sum is shown on each turn.",

                "[INSTRUCTIONS]:\n\n" +
                        "The player can flip any row or column" +
                        " any number of times.\n" +
                        "                                       \n" +
                        "To flip ROWs, type 'r' followed by the" +
                        " ROW number(s).\n" +
                        "To flip COLUMNs, type 'c' followed by the" +
                        " COLUMN number(s).",

                "[EXAMPLE]:\n\n" +
                        "r123      ->  flip rows 1, 2, and 3\n" +
                        "c24       ->  flip cols 2 and 4\n" +
                        "r2c34r2   ->  flip row 2 then\n" +
                        "              flip col 3 and 4 then\n" +
                        "              flip row 2"
        );
    }

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
                printInformation(InformationDepth.SHORT);
                cmd = this.scanner.nextLine();
                cmdMat = cmdPat.matcher(cmd);
            }

            // Check if entire Cmd is Only Valid Cmd's
            StringBuilder cmds = new StringBuilder();
            do cmds.append(cmdMat.group(1));
            while (cmdMat.find());
            if (!cmd.contentEquals(cmds)) {
                printInformation(InformationDepth.SHORT);
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
                        return this.quit();
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
}
