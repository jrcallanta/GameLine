package game;

public class GameInformation {
    final private String goal;
    final private String instruction;
    final private String example;

    public GameInformation(String goal, String instruction, String example) {
        this.goal = goal;
        this.instruction = instruction;
        this.example = example;
    }

    public String getGoal() {
        return goal;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getExample() {
        return example;
    }
}
