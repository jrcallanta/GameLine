import game.Game;
import game.Score;
import game.Scoreboard;
import matriks.Matriks;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
        Game someGame = new Matriks();
        someGame.selectDifficulty();

        while (someGame.getDifficulty() != null) {
            someGame.reset();
            Score score = someGame.play();

            if (score != null) scoreboard.addScore(someGame.getGameName(), score);
            scoreboard.print();

            if (!someGame.askPlayAgain()) break;
        }
    }
}