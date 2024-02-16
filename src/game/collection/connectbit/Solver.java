package game.collection.connectbit;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Solver {
    final private Generator generator;
    private List<Solution> solutions;

    public Solver(Generator generator) {
        this.generator = generator;
        this.solutions = new ArrayList<>();
        List<String> binaries = this.generator.getTargetsBinaries();
        //System.out.println(binaries);

        Stack<String> starter = new Stack<>();
        starter.add(binaries.remove(0));

        this.solutions = solve(starter, binaries, this.solutions);
    }

    private List<Solution> solve(List<String> current, List<String> remaining, List<Solution> foundSolutions) {
        String spacing = String.format("%" + (4 * current.size()) + "s", "").replaceAll(" {4}", "-   ");
        //System.out.println(spacing + "r:" + remaining);
        //System.out.println(spacing + "c:" + current);

        if (remaining.isEmpty()) {
            String lastItem = current.get(current.size() - 1);
            String trueLastItem = this.generator.getTargetsBinaries().get(this.generator.getCount() - 1);
            if (lastItem.equals(trueLastItem)) {
                List<Integer> solution = current
                        .stream()
                        .map(val -> Integer.parseInt(val, 2))
                        .collect(Collectors.toList());

                //System.out.printf(spacing + "SOLUTION FOUND: %s\n\n", solution);
                foundSolutions.add(new Solution(solution));
            }
            else {
                //System.out.println(spacing + "DEAD END\n");
            }

            return foundSolutions;
        }

        int bitIndex = 0;
        while (bitIndex < generator.getBitCount()) {
            StringBuilder sb = new StringBuilder(current.get(current.size() - 1));
            sb.setCharAt(bitIndex, sb.charAt(bitIndex) == '0' ? '1' : '0');
            //System.out.print(spacing + "s[" + current.get(current.size() - 1) + "]:" + sb);
            if (
                    remaining.contains(sb.toString()) &&
                            (remaining.indexOf(sb.toString()) != remaining.size() - 1 || remaining.size() == 1)
            ) {
                //System.out.println();
                int ind = remaining.indexOf(sb.toString());
                List<String> currentCopy = new ArrayList<>(current);
                List<String> remainingCopy = new ArrayList<>(remaining);
                currentCopy.add(remainingCopy.remove(ind));
                foundSolutions = solve(currentCopy, remainingCopy, foundSolutions);
            }
            else {
                //System.out.println(" x");
            }
            bitIndex++;
        }

        //System.out.println(spacing + "DEAD END\n");
        return foundSolutions;
    }
    public List<Solution> getSolutions() {
        return this.solutions;
    }
}
