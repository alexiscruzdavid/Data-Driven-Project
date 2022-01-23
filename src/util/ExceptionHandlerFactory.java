package ooga.util;

import javafx.scene.control.Alert.AlertType;

import java.util.ResourceBundle;

/** Class that helps to create alerts that handle exceptions */
public class ExceptionHandlerFactory {

  private AlertFactory alertFactory;
  private ResourceBundle resources;

  /** Create an instance of the factory. */
  public ExceptionHandlerFactory(ResourceBundle resources) {
    this.resources = resources;
    alertFactory = new AlertFactory(resources);
  }

  /**
   * Given an exception, create an Alert for that exception, and display it.
   *
   * @param title is the title of the exception
   * @param description is a description of the exception to be displayed by the user
   */
  public void handle(String title, String description) {
    alertFactory.makeAlertShow(title, description, AlertType.ERROR);
  }
}
