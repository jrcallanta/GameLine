import game.Game;
import game.Score;
import game.Scoreboard;
import matriks.Matriks;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
        Game someGame = new Matriks();

        runner: while (true) {
            Score score = someGame.play();
            scoreboard.addScore(someGame.getGameName(), score);
            scoreboard.print();
            switch (someGame.askPlayAgain()) {
                case "YES" -> { }
                case "CHANGE_DIFFICULTY" -> {
                    if (someGame.selectDifficulty() == null)
                        break runner;
                }
                default -> {
                    break runner;
                }
            }
            someGame.reset();
        }
    }
}