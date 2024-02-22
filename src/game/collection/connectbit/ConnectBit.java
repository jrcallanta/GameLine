package game.collection.connectbit;

import game.types.TimedGame;
import game.scoring.Score;
import game.scoring.TimeScore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectBit extends TimedGame {
    final private Generator generator;

    public ConnectBit () {
        this.generator = new Generator();
    }

    @Override
    public void reset() {
        super.reset();

        switch (this.difficulty) {
            case EASY ->
                    generator.generate(6);
            case MEDIUM ->
                    generator.generate(8);
            case HARD ->
                    generator.generate(10);
            default ->
                    throw new RuntimeException("NoDifficultySet");
        }

        this.generator.solve();
    }
    @Override
    public Score play() {
        this.countDown();
        this.startTimer();

        List<Integer> answer;
        while (true) {
            this.generator.printTargets();
            System.out.println();
            this.generator.printEnds();
            System.out.println();

            answer = new ArrayList<>();
            String answerLine = this.scanner.nextLine();
            if (answerLine.equalsIgnoreCase("q") || answerLine.equalsIgnoreCase("quit")) {
                return this.quit();
            }

            Scanner lineScanner = new Scanner(answerLine);
            while (lineScanner.hasNextInt())
                answer.add(lineScanner.nextInt());

            if (
                    answer.size() != generator.getTargets().size()
                    || !generator.getSolutionsAsIntegerLists().contains(answer)
            ) {
                System.out.println("INCORRECT!\n");
            } else {
                this.stopTimer();
                break;
            }
        }

        this.printWinnerResults(answer, generator.getSolutionsAsIntegerLists());
        return new TimeScore(
            this.difficulty,
            this.getElapsedTime()
        );
    }


    @Override
    public String getGameName() {
        return "CONNECT BIT";
    }

    @Override
    public void printInstructions(InstructionDepth depth) {
        switch (depth) {
            case FULL -> {
                System.out.println();
                System.out.println("==============================================");
                System.out.println("[GOAL]: You are given a list of integers,");
                System.out.println("  all of which are representable by 4 bits.");
                System.out.println("  Arrange the integers such that any two");
                System.out.println("  consecutive integers only differ by 1 bit.");
                System.out.println("  The first and last integers will always be");
                System.out.println("  in their correct location.");
                System.out.println();
                System.out.println("[INSTRUCTIONS]: To submit an answer, simply");
                System.out.println("  type in a space-separated list of integers");
                System.out.println("  in any order you think satisfies");
                System.out.println("  the conditions.");
                System.out.println();
                System.out.println("  ex:   Given the following list: ");
                System.out.println("        [3  4  7  6  5]");
                System.out.println("        3  -  -  -  5");
                System.out.println();
                System.out.println("        The binary representations are");
                System.out.println("        3 -> 0011   4 -> 0100   5 -> 0101");
                System.out.println("        6 -> 0110   7 -> 0111");
                System.out.println();
                System.out.println("        3 is one bit away from 7, 0011 -> 0111");
                System.out.println("        7 is one bit away from 6, 0111 -> 0110");
                System.out.println("        6 is one bit away from 4, 0110 -> 0100");
                System.out.println("        4 is one bit away from 5, 0100 -> 0101");
                System.out.println();
                System.out.println("        Therefore, the solution is [3 7 6 4 5]");
                System.out.println();
                System.out.println("==============================================");
                System.out.println();
            }

            case SHORT -> {
                System.out.println("[GOAL]: Arrange the integers such that any two");
                System.out.println("  consecutive integers only differ by 1 bit.");
                System.out.println("  The first and last integers will always be");
                System.out.println("  in their correct location.");
                System.out.println("[INSTRUCTIONS]: Type in a space-separated");
                System.out.println("  list of integers in any order you think");
                System.out.println("  satisfies the conditions.");
                System.out.println();
            }

            default -> {}
        }
    }

    private void printWinnerResults (List<Integer> finalSolution, List<List<Integer>> allSolutions) {
        System.out.println("CORRECT!\n");
        String s = String.format("SOLUTION: %s", finalSolution);
        String line = String.format("%" + s.length() + "s", "").replace(" ", "-");
        System.out.println(line);
        System.out.printf("%s\n",s);
        System.out.print("BIT FLIPS:\n");
        Solution solution = new Solution(finalSolution);
        for (int i = 0; i < finalSolution.size(); i ++) {
            String v = solution.getElements().get(i) + " -> " + solution.getBinaries().get(i);
            System.out.printf("%" + s.length() + "s\n", v);
        }
        System.out.print("ALL SOLUTIONS:\n");
        allSolutions.forEach(sol ->
                System.out.printf("%" + s.length() + "s\n", sol)
        );
        System.out.println(line);
    }
}
