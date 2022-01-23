package ooga.util;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class used to create alert messages.
 */
public class AlertFactory {

  private ResourceBundle myResources;

  /**
   * alertfactory constructor
   */
  public AlertFactory(ResourceBundle resources) {
    myResources = resources;
  }

  /**
   * Creates a new alert object.
   *
   * @param title   of alert
   * @param message displayed in alert
   * @param type    of the alert
   */

  public void makeAlertWait(String title, String message, Alert.AlertType type) {
    Alert alert = createAlertContents(title, message, type);
    alert.showAndWait();
  }

  private Alert createAlertContents(String title, String message, AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(title);
    alert.setContentText(message);
    return alert;
  }

  public void makeAlertShow(String title, String message, Alert.AlertType type) {
    Alert alert = createAlertContents(title, message, type);
    alert.show();
  }
}
