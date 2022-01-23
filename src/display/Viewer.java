package ooga.display;

import java.util.Iterator;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ooga.controller.Controller;

public interface Viewer {

  /**
   * Triggers the setup of the starting view of the game.
   *
   * @return a Scene with all the desired nodes and objects added to it.
   */
  Scene setupView();

  /**
   * Creates the initial display grid in the display for the game.
   *
   * @param width        the width of the grid
   * @param height       the height of the grid
   * @param gridIterator an iterator that can integrate through all the current states in the grid
   */

  void createDisplayGrid(int width, int height, Iterator<Integer> gridIterator);

  /**
   * Adds a key listener, so that events in the rest of the program can be triggered upon the press
   * of a key.
   *
   * @param handler the event to be triggered by the press of a key
   */
  void addKeyListener(EventHandler<KeyEvent> handler);

  Scene getScene();

  /**
   * Displays the next state of the game.
   *
   * @param i     the row of the cell to update
   * @param j     the col of the cell to update
   * @param state the state of the cell
   */
  void updateCell(int i, int j, int state);

  /**
   * Sets up the button panel for the view to be displayed.
   */
  void buttonPanel(Controller controller);


  /**
   * Adds a mouse listener to the display cells, allowing the functionality of clicks triggering
   * events in the back end.
   *
   * @param handler the event handler that will be triggered in response to a mouse event
   */
  void addMouseListenerToDisplayCells(EventHandler<MouseEvent> handler);

  /**
   * Updates the view of the game with the current score and the grid states.
   *
   * @param gridIterator the iterator object that contains all the information about the states of
   *                     the cells
   * @param currentScore the current score of the game at the time of the update
   */
  void updateView(Iterator<Integer> gridIterator, int currentScore);

  /**
   * Displays an error in the view to the user when called.
   *
   * @param message the error message to be displayed
   */
  void displayError(String message);

  /**
   * Sets the initial configuration information specified in the configuration file for the view.
   *
   * @param imgMap the images used for displaying objects in the view
   */
  void setConfigInfo(Map<String, String> imgMap, String gameTitle);

  /**
   * Gets the current cheat code information from the view.
   * @return the cheat code as a string
   */
  String getCurrentCheatCode();

  /**
   * Display that the user has lost the game.
   */
  void displayEndGame();

  void displaySaveAlert();

}





