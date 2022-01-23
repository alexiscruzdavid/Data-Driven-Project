package ooga.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import org.junit.jupiter.api.Test;

/**
 * Class to test the functionality of the button factory class
 *
 * @author Kyle White
 */
class ButtonFactoryTest {

  @Test
  void makeButton() {
    new JFXPanel();
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        ResourceBundle buttonResources = ResourceBundle.getBundle(
            "ooga.resources.Buttons");
        ResourceBundle resources = ResourceBundle.getBundle(
            "ooga.resources.English");
        NodeFactory factory = new NodeFactory(resources, buttonResources);
        Button test = factory.makeButton("Test", this);
        assertEquals(test.getText(), "test");
        assertEquals(test.getId(), "Test");
      }
    });
  }

}