package ooga.display;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ooga.controller.ProfileController;
import util.DukeApplicationTest;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import javafx.scene.control.Button;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class ProfileMenuTest extends DukeApplicationTest {

    private ProfileController menuCtrl;
    private ProfileMenu menu;
    private String language = "English";

    @Override
    public void start(Stage stage) {
        Stage newStage = new Stage();
        menuCtrl = new ProfileController(language, newStage);
        menu = menuCtrl.getProfileMenu();
        newStage.setScene(menu.setupDisplay());
    }

    @Test
    public void testMissingInfoCreateAccount() {
        Button button = lookup("#CreateAccountButton").query();
        clickOn(button);
        String expected = "Please enter all information in text fields!";
        assertEquals(getDialogMessage(), expected);
    }

    @Test
    public void testMissingInfoLogin() {
        Button button = lookup("#LoginButton").query();
        clickOn(button);
        String expected = "Please enter all information in text fields!";
        assertEquals(getDialogMessage(), expected);
    }

    @Test
    public void testCreateAccountButton() {
        TextField usernameField = lookup("#CreateAccUsernameField").query();
        usernameField.setText("name");
        TextField passwordField = lookup("#CreateAccPasswordField").query();
        passwordField.setText("password");
        ComboBox<String> lang = lookup("#LanguageComboBox").queryComboBox();
        clickOn(lang);
        clickOn(lang.getItems().get(0));
        Button button = lookup("#CreateAccountButton").query();
        clickOn(button);
        String expected = "Registration Successful!";
        assertEquals(getDialogMessage(), expected);

    }
    @Test
    public void testLogInButton() {
        TextField usernameField = lookup("#LogInUserNameField").query();
        usernameField.setText("name");
        TextField passwordField = lookup("#LogInPasswordField").query();
        passwordField.setText("password");
        Button button = lookup("#LoginButton").query();
        clickOn(button);
        String expected = "Welcome Back!";
        assertEquals(getDialogMessage(), expected);
    }

    @Test
    public void testMissingPlayer() {
        TextField usernameField = lookup("#LogInUserNameField").query();
        usernameField.setText("aeifjqiufajbaijfbijfbkjqwbuiecbqwiuciuqwcb");
        TextField passwordField = lookup("#LogInPasswordField").query();
        passwordField.setText("qsbfjbqwoifioqweoifhqwoipfhpqiohwoieoirbouqwuoeuorqweroih");
        Button button = lookup("#LoginButton").query();
        clickOn(button);
        String expected = "Player was not found try again";
        assertEquals(getDialogMessage(), expected);
    }
}