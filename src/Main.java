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
                game.printInstructions();
                game.waitToContinue("Press ENTER to continue...");
                game.selectDifficulty();

                while (game.getDifficulty() != null) {
                    game.reset();
                    game.waitToContinue("Press ENTER to start...");
                    Score score = game.play();
                    System.out.println("\n\n");

                    if (score != null) scoreboard.addScore(game.getGameName(), score);
                    scoreboard.printByGame(game.getGameName());
                    System.out.println("\n\n");

                    if (!game.askPlayAgain()) break;
                    System.out.println("\n\n");
                }

                game = gs.selectUsingScanner(scanner);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scanner.close();
    }
}