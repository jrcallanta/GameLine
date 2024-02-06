package game;

public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    final private int value;

    Difficulty(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (this.value) {
            case 1 -> {
                return "EASY";
            }
            case 2 -> {
                return "MEDIUM";
            }
            case 3 -> {
                return "HARD";
            }
            default -> {
                return null;
            }
        }
    }

}
