package ooga.display;

import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ooga.controller.GameSelectorMenuController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import javafx.scene.control.Button;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameSelectorMenuTest extends DukeApplicationTest {

    private String language = "English";


    @Override
    public void start(Stage stage) {
        Stage newStage = new Stage();
        GameSelectorMenuController menuCtrl = new GameSelectorMenuController(language, newStage);
        GameSelectorMenu menu = menuCtrl.getGameSelectorMenu();
        newStage.setScene(menu.setupDisplay());
    }

    @Test
    public void testGoButtonBeforeParameters() {
        String expected = "Please Select a Game";
        Button button = lookup("#GoButton").query();
        clickOn(button);
        assertEquals(expected, getDialogMessage());
    }

    @Test
    public void testGameSelect() {
        String expected = "2048";
        ComboBox<String> comboBox = lookup("#GameComboBox").queryComboBox();
        clickOn(comboBox);
        clickOn(comboBox.getItems().get(0));
        assertEquals(comboBox.getValue(), expected);
    }

    @Test
    public void testEditProfileButton() {
        Button button = lookup("#editProfile").query();
        clickOn(button);
    }

}
