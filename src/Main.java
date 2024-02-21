import game.Game;
import game.scoring.Score;
import game.scoring.Scoreboard;
import game.GameSelector;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();

        Scanner scanner = new Scanner(System.in);
        GameSelector gs = new GameSelector();
        Game game = gs.selectUsingScanner(scanner);

        try {
            while (game != null) {
                game.selectDifficulty();

                while (game.getDifficulty() != null) {
                    game.reset();
                    Score score = game.play();

                    if (score != null) scoreboard.addScore(game.getGameName(), score);
                    scoreboard.printByGame(game.getGameName());

                    if (!game.askPlayAgain()) break;
                }

                game = gs.selectUsingScanner(scanner);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scanner.close();
    }
}