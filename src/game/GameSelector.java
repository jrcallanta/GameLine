package game;

import game.collection.connectbit.ConnectBit;
import game.collection.matriks.Matriks;
import menu.Menu;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class GameSelector {
    final private Menu menu;
    private Scanner scanner;
    public GameSelector() {
        // Configure Menu
        this.menu = new Menu();
        this.menu.setPrompt("CHOOSE A GAME:");
        this.menu.setBorderChar("-");

        // Add All Game Options Menu
        this.menu.addOption("MATRIKS", "1");
        this.menu.addOption("CONNECT BIT", "2");
        this.menu.addOption("SANSA", "3");

        // Add secret QUIT option
        this.menu.addSecretOption("QUIT", Arrays.asList("quit", "q"));
    }

    public Game select () throws IOException {
        if (this.scanner == null) throw new IOException("NoScannerProvided");
        return selectUsingScanner(this.scanner);
    }
    public Game selectUsingScanner(Scanner scanner) {
        Game game = null;
        // Return respective Game
        switch(this.menu.ask(scanner)) {
            case "MATRIKS" ->
                game = new Matriks();

            case "CONNECT BIT" ->
                game = new ConnectBit();

            default -> { }
        }

        if (game != null) game.setScanner(scanner);
        return game;
    }

    public void setScanner (Scanner scanner) {
        this.scanner = scanner;
    }
}
