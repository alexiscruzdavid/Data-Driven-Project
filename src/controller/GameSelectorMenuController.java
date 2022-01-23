package ooga.controller;

import javafx.stage.Stage;
import ooga.display.GameSelectorMenu;

/**
 * A controller for managing the Game Selector Menu and
 * the proceeding displays
 */
public class GameSelectorMenuController {
    private final GameSelectorMenu menu;

    public GameSelectorMenuController(String language, Stage stage) {
        menu = new GameSelectorMenu(language);
        stage.setScene(menu.setupDisplay());
        stage.show();
    }

    public GameSelectorMenu getGameSelectorMenu() {
        return menu;
    }
}
