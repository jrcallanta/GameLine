package game.collection.connectbit;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    final private List<Integer> elements;
    private final List<String> binaries;

    public Solution(List<Integer> solution) {
        this.elements = solution;
        this.binaries = new ArrayList<>();

        for (int value : solution) {
            this.binaries.add(
                    String
                            .format("%4s", Integer.toBinaryString(value))
                            .replace(" ", "0")
            );
        }
    }
    public List<Integer> getElements() {
        return this.elements;
    }
    public List<String> getBinaries() {
        return this.binaries;
    }
    @Override
    public String toString () {
        return this.elements.toString();
    }
}
