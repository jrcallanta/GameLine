package gameSelector;

import game.Game;
import matriks.Matriks;
import menu.Menu;

import java.util.Arrays;

public class GameSelector {
    private Menu menu;
    public GameSelector() {
        // Configure Menu
        this.menu = new Menu();
        this.menu.setPrompt("CHOOSE A GAME:");
        this.menu.setBorderChar("-");
        this.menu.setInvalidFeedback("***** Not valid selection. Try Again. *****");

        // Add All Game Options Menu
        this.menu.addOption("MATRIKS", "1");

        // Add secret QUIT option
        this.menu.addSecretOption("QUIT", Arrays.asList("quit", "q"));
    }
    public Game select () {

        // Return respective Game
        switch(this.menu.ask()) {
            case "MATRIKS" -> {
                return new Matriks();
            }
            default -> {
                return null;
            }
        }
    }

}
