package game.collection.matriks;

import game.GameInformation;
import game.scoring.Score;
import game.scoring.TimeScore;
import game.types.TimedGame;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Matriks extends TimedGame {
    private MatriksSquare matriks;
    private int numOfFlips;
    private int numOfTurns;

    private Pattern validPat;
    private Pattern flipPat;
    private List<String> quitStrings;

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
                        "The matrix board has 2Nx2N elements," +
                        " consisting of the characters A B C and D." +
                        " Each character appears N*N times, allowing" +
                        " for a perfect square with each quadrant" +
                        " having a designated character. Manipulate" +
                        " the rows and columns so that each quadrant" +
                        " contains only its respective character. You" +
                        " decide which characters go in which quadrant.",

                "[INSTRUCTIONS]:\n\n" +
                        "Type in a sequence of commands to flip" +
                        " any row or column any number of times." +
                        " Type 'r' followed by the ROW number(s)" +
                        " to flip ROWs. Type 'c' followed by the" +
                        " COLUMN number(s) to flip COLs.            \n" +
                        "                                           \n" +
                        "r123      ->  flip rows 1, 2, and 3        \n" +
                        "c24       ->  flip cols 2 and 4            \n" +
                        "r2c34r2   ->  flip row 2 then              \n" +
                        "              flip col 3 and 4 then        \n" +
                        "              flip row 2",

                "[EXAMPLE]:\n\n" +
                        "Given the following matrix:                \n" +
                        "[                                          \n" +
                        "    A A C B                                \n" +
                        "    A A C B                                \n" +
                        "    C B D D                                \n" +
                        "    D D B C                                \n" +
                        "]                                          \n" +
                        "                                           \n" +
                        "The matrix can be solved in two flips. First," +
                        " flip the third row (r3), putting the" +
                        " Ds in the bottom left quadrant. Then flip" +
                        " either the third column (c3) or fourth" +
                        " column (c4), to put the Cs and Bs in their" +
                        " respective quadrants. This entire sequence" +
                        " can also be accomplished with one command" +
                        " (r3c3 or r3c4)."
        );

        // Quit Strings
        this.quitStrings = List.of(
                "quit"
                //"q"
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

        // Regex Strings
        List<String> validStr = List.of(
                "^(?: *[r|c][1-%d]+)+$".formatted(this.matriks.getMatrixSize()),
                "(?:(?:^\\s*(%s)\\s*$))".formatted(this.quitStrings.stream().collect(Collectors.joining("|")))
        );
        String flipStr = "([r|c])([1-6]+)";

        // Generate Regex Patterns
        this.validPat = Pattern.compile(
                validStr.stream().collect(Collectors.joining("|")),
                Pattern.CASE_INSENSITIVE
        );
        this.flipPat = Pattern.compile(
                flipStr,
                Pattern.CASE_INSENSITIVE
        );
    }

    @Override
    public Score play() {
        this.countDown();
        this.startTimer();

        String flipCountString = "FLIPS: " + this.numOfFlips;
        System.out.println(this.matriks);
        System.out.println(flipCountString);

        while(true) {
            System.out.print("> ");
            String cmd = this.scanner.nextLine();
            Matcher valid = validPat.matcher(cmd);

            if (!valid.find())
                continue;

            cmd = valid.group().toLowerCase().trim();
            if (quitStrings.contains(cmd))
                return this.quit();

            this.numOfTurns++;
            Matcher flipMatcher = flipPat.matcher(cmd);
            while (flipMatcher.find()) {
                int[] ind = Arrays
                        .stream(flipMatcher.group(2).split(""))
                        .mapToInt(i -> Integer.parseInt(i) - 1).toArray();
                switch(flipMatcher.group(1).toLowerCase()) {
                    case "r" -> {
                        for(int i : ind) matriks.flipRow(i);
                        this.numOfFlips++;
                    }
                    case "c" -> {
                        for(int i : ind) matriks.flipCol(i);
                        this.numOfFlips++;
                    }
                    default -> { }
                }
            }

            if (!matriks.targetReached()) {
                System.out.println(this.matriks);
                System.out.println(flipCountString);
                continue;
            }

            this.stopTimer();
            this.matriks.print();
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
        int seconds = (int) TimeUnit.SECONDS.convert(this.getElapsedTime(), TimeUnit.MILLISECONDS);
        StringBuilder sb = new StringBuilder();
        sb
                .append("SQUARE SOLVED!!\n")
                .append("TIME: " + seconds/60 + "m " + seconds%60 + "s\n")
                .append("TURNS: " + this.numOfTurns + "\n")
                .append("FLIPS: " + this.numOfFlips);
        System.out.println(sb);
    }
}
