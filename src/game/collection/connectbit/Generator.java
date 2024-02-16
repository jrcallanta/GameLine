package game.collection.connectbit;

import java.util.*;
import java.util.stream.Collectors;

public class Generator {
    private int bitCount;
    private int count;
    private List<Integer> targets;
    private List<Solution> solutions;
    private HashMap<Integer, String> map;

    public Generator () {
        this.targets = new ArrayList<>();
        this.solutions = new ArrayList<>();
        this.map = new HashMap<>();
    }

    public void generate(List<Integer> testList) {
        this.count = testList.size();

        int largestInt = testList.stream().sorted().toList().get(testList.size() - 1);
        int mostBits = Integer.toBinaryString(largestInt).length();
        this.bitCount = mostBits % 4 == 0
                ? mostBits
                : mostBits + 4 - (mostBits % 4);

        Solution base = new Solution(testList);
        for(int i = 0; i < base.getElements().size(); i++) {
            this.map.put(base.getElements().get(i), base.getBinaries().get(i));
        }
        this.solutions = new ArrayList<>(List.of(base));

        Random ran = new Random();
        List<Integer> bag = new ArrayList<>(base.getElements());
        this.targets.add(bag.remove(0));
        while(bag.size() > 1) {
            this.targets.add(bag.remove(ran.nextInt(0, bag.size() - 1)));
        }
        this.targets.add(bag.remove(0));
    }
    public void generate (int count) {
        this.generate(count, 4);
    }
    public void generate (int count, int bitCount) {
        this.count = count;
        this.bitCount = bitCount;
        this.targets = new ArrayList<>();
        this.solutions = new ArrayList<>();
        this.map = new HashMap<>();
        Random ran = new Random();


        // Start with random integer representable within bitCount
        int value = ran.nextInt(0, (int) Math.pow(2, bitCount));
        StringBuilder valueBinary = new StringBuilder(
                String
                        .format("%4s", Integer.toBinaryString(value))
                        .replace(" ", "0")
        );


        // Initialize the base solution
        List<Integer> baseSolution = new ArrayList<>();
        baseSolution.add(value);
        this.map.put(value, valueBinary.toString());

        int randomBitIndex = -1;
        for (int i = 1; i < count; i++) {
            int nextBitIndex = ran.nextInt(0, bitCount);
            while (nextBitIndex == randomBitIndex) {
                nextBitIndex = ran.nextInt(0, bitCount);
            }
            randomBitIndex = nextBitIndex;

            char currentBitValue = valueBinary.charAt(randomBitIndex);
            valueBinary.setCharAt(randomBitIndex, currentBitValue == '0' ? '1' : '0');

            int newValue = Integer.parseInt(valueBinary.toString(), 2);
            baseSolution.add(newValue);
            this.map.put(newValue, valueBinary.toString());
        }
        this.solutions = new ArrayList<>(List.of(new Solution(baseSolution)));


        // Randomize elements in base solution into targets
        List<Integer> bag = new ArrayList<>(baseSolution);
        this.targets.add(bag.remove(0));
        while(bag.size() > 1) {
            this.targets.add(bag.remove(ran.nextInt(0, bag.size() - 1)));
        }
        this.targets.add(bag.remove(0));
    }

    public void solve() {
        Solver solver = new Solver(this);
        this.solutions = solver.getSolutions();
    }
    public int getBitCount() {
        return this.bitCount;
    }
    public int getCount() {
        return this.count;
    }
    public List<Integer> getTargets() {
        return this.targets;
    }
    public List<String> getTargetsBinaries() {
        List<String> binaries = new ArrayList<>();
        this.targets.forEach(target -> binaries.add(this.map.get(target)));
        return binaries;
    }
    public List<Solution> getSolutions() {
        return this.solutions;
    }
    public List<List<Integer>> getSolutionsAsIntegerLists() {
        return this.solutions.stream().map(Solution::getElements).collect(Collectors.toList());
    }
    public List<Integer> getSolution() {
        return this.solutions.get(0).getElements();
    }
    public List<String> getSolutionBinaries() {
        List<String> solutionStrings = new ArrayList<>();
        this.solutions.get(0).getElements().forEach(target -> solutionStrings.add(this.map.get(target)));

        return solutionStrings;
    }
    public void printEnds() {
        StringBuilder sb = new StringBuilder();
        List<Integer> solution = this.solutions.get(0).getElements();
        sb.append(solution.get(0)).append("  ");
        sb.append("-  ".repeat(Math.max(0, solution.size() - 2)));
        sb.append(solution.get(solution.size() - 1));

        System.out.print(sb);
    }

    public void printTargets() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("[")
                .append(this.targets.get(0))
                .append("  ");
        for (int i = 1; i < this.targets.size() - 1; i++ ) {
            sb
                    .append(this.targets.get(i))
                    .append("  ");
        }
        sb
                .append(this.targets.get(this.targets.size() - 1))
                .append("]");

        System.out.print(sb);
    }
}
