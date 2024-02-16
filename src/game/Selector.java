package game;

import game.collection.connectbit.ConnectBit;
import game.collection.matriks.Matriks;
import menu.Menu;

import java.util.Arrays;

public class Selector {
    final private Menu menu;
    public Selector() {
        // Configure Menu
        this.menu = new Menu();
        this.menu.setPrompt("CHOOSE A GAME:");
        this.menu.setBorderChar("-");

        // Add All Game Options Menu
        this.menu.addOption("MATRIKS", "1");
        this.menu.addOption("CONNECT BIT", "2");

        // Add secret QUIT option
        this.menu.addSecretOption("QUIT", Arrays.asList("quit", "q"));
    }
    public Game select () {

        // Return respective Game
        switch(this.menu.ask()) {
            case "MATRIKS" -> {
                return new Matriks();
            }
            case "CONNECT BIT" -> {
                return new ConnectBit();
            }
            default -> {
                return null;
            }
        }
    }

}
