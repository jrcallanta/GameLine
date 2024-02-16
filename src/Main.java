import game.Game;
import game.scoring.Score;
import game.scoring.Scoreboard;
import game.Selector;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
        Selector selector = new Selector();
        Game game = selector.select();

        while (game != null) {
            game.selectDifficulty();

            while (game.getDifficulty() != null) {
                game.reset();
                Score score = game.play();

                if (score != null) scoreboard.addScore(game.getGameName(), score);
                scoreboard.printByGame(game.getGameName());

                if (!game.askPlayAgain()) break;
            }

            game = selector.select();
        }
    }
}