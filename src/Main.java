import game.Game;
import game.Score;
import game.Scoreboard;
import gameSelector.GameSelector;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();
        GameSelector gameSelector = new GameSelector();
        Game game = gameSelector.select();

        while (game != null) {
            game.selectDifficulty();

            while (game.getDifficulty() != null) {
                game.reset();
                Score score = game.play();

                if (score != null) scoreboard.addScore(game.getGameName(), score);
                scoreboard.print();

                if (!game.askPlayAgain()) break;
            }

            game = gameSelector.select();
        }
    }
}