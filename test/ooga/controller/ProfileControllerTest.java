package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

/**
 * Class to test the functionality of the ProfileMenuController
 *
 * @author Kyle White
 */
class ProfileControllerTest {

  @Test
  void getProfileMenu() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Stage s = new Stage();
        ProfileController controller = new ProfileController("English", s);
        assertNotNull(controller.getProfileMenu());
      }
    });
  }

}