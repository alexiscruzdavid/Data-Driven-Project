package ooga.display;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.controller.ProfileEditorController;
import util.DukeApplicationTest;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import javafx.scene.control.Button;
import javafx.scene.control.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditProfileMenuTest extends DukeApplicationTest {

  private ProfileEditorController menuCtrl;
  private ProfileEditorMenu menu;
  private String language = "English";
  private Profile testProfile;

  @Override
  public void start(Stage stage) {
    testProfile = new Profile();
    menuCtrl = new ProfileEditorController(language, stage, testProfile);
    menu = menuCtrl.getProfileEditorMenu();
    stage.setScene(menu.setupDisplay());
  }

  @Test
  public void testMissingInfoEditAccount() {
    Button button = lookup("#saveChanges").query();
    clickOn(button);
    String expected = "Please enter all information in text fields!";
    assertEquals(getDialogMessage(), expected);
  }

  @Test
  public void testSaveChangesButton(){
    TextField usernameField = lookup("#ProfileUsername").query();
    usernameField.setText("name");
    TextField passwordField = lookup("#ChangePasswordField").query();
    passwordField.setText("password");
    ComboBox<String> lang = lookup("#ChangeLangComboBox").queryComboBox();
    clickOn(lang);
    clickOn(lang.getItems().get(0));
    ColorPicker theme = lookup("#EditColor").query();
    //theme.setValue(Color.WHITE);
    Button button = lookup("#saveChanges").query();
    clickOn(button);
    String expected = "Changes Saved!";
    assertEquals(getDialogMessage(), expected);
  }

}