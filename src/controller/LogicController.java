package ooga.controller;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.data.GameData;
import ooga.display.Display;
import ooga.display.Viewer;
import ooga.engine.Engine;
import ooga.engine.GameEngine;
import ooga.exceptions.EndGameException;
import ooga.exceptions.ReflectionException;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.grid.ImmutableGrid;
import ooga.gamelogic.initialstates.InitialStates;


/**
 * Controls the GameEngine portion of the program. Updates the GameEngine and initializes the game
 * setup. Provides communication between the GameEngine and the view for the purpose of sending
 * information about the current game state to the view to be displayed.
 */
public class LogicController {

  private static final int ZERO_STARTING_SCORE = 0;
  private static final String INITIAL_STATES_LOGIC_PATH = "ooga.gamelogic.initialstates.%s";
  private static final String CHEAT_CODES_KEY = "CheatCodes";
  private static final String CHEAT_CODES_MESSAGE = "cheat code";
  private static final String INITIAL_STATES_MESSAGE = "initial states";

  private Engine gameEngine;
  private Viewer view;
  private InitialStates myInitialStatesGenerator;
  private GameData gameData;
  private boolean isSpeedUp = false;
  private boolean isSlowDown = false;

  private StringProperty timer = new SimpleStringProperty();

  private final ResourceBundle validProperties = ResourceBundle.getBundle(
      "ooga.resources.sim_valid_parameters");

  /**
   * Initializes the game engine from the configuration data passed in.
   *
   * @param configData starting data to create the game engine with
   */
  public void createGameEngine(GameData configData) throws ReflectionException {
    gameData = configData;
    int boardWidth = gameData.getNumberOfColumns();
    int boardHeight = gameData.getNumberOfRows();
    initializeInitialState(gameData.getInitialStates());
    Grid grid = myInitialStatesGenerator.generateInitialState(boardWidth, boardHeight, gameData);
    gameEngine = new GameEngine();
    gameEngine.setupGame(grid, gameData);
  }

  /**
   * Sets the view instance variable as the current display for the game.
   *
   * @param display the current display for the game
   */
  public void setDisplay(Viewer display) {
    ((Display) display).bindTimer(timer);
    this.view = display;
  }


  // gets the grid currently in use if the game engine is not null
  protected ImmutableGrid getImmutableGrid() {
    if (gameEngine != null) {
      return gameEngine.getImmutableGrid();
    }
    return null;
  }


  /**
   * Updates the game by one "step", applying the game logic and changing to the next game state.
   *
   * @return boolean indicating if the step has been successfully completed
   * @throws InvalidParameterException indicating that an invalid parameter has been detected in the
   *                                   model
   * @throws ReflectionException       indicating that there was an error in creating the correct
   *                                   objects for the next state via reflection
   * @throws EndGameException          indicating that the game has been lost
   */
  public boolean step() throws InvalidParameterException, ReflectionException, EndGameException {
    if (getImmutableGrid() != null && getImmutableGrid().iterator().hasNext()) {
      String cheatCode = checkCurrentCheatCode();
      gameEngine.nextGameState(cheatCode);
      timer.setValue(getTimerString());
      view.updateView(getImmutableGrid().iterator(), getCurrentScore());
    }
    return true;
  }

  /**
   * Sets up the view with the information obtained from the configuration file, including state
   * pictures, the timer, and the initial grid.
   */
  public void setupViewWithInformation() {
    view.setConfigInfo(gameData.getStatePictures(), gameData.getGame());
    timer.setValue(getTimerString());
    view.createDisplayGrid(gameData.getNumberOfColumns(), gameData.getNumberOfRows(),
        getImmutableGrid().iterator());
    view.updateView(getImmutableGrid().iterator(), ZERO_STARTING_SCORE);
  }

  /**
   * Sets values of grid based on new grid inputted.
   *
   * @param newGrid grid of new cell states
   */
  public void applyNewGridValues(Grid newGrid) {
    gameEngine.resetGame(newGrid);
  }


  private void initializeInitialState(String initialState) {
    try {
      myInitialStatesGenerator = (InitialStates) Class.forName(
          String.format(INITIAL_STATES_LOGIC_PATH, initialState)).getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
      throw new ReflectionException(INITIAL_STATES_MESSAGE);
    }
  }

  /**
   * Restarts the game with new initial states.
   */
  public void restartGame() {
    Grid resetGrid = myInitialStatesGenerator.generateInitialState(gameData.getNumberOfColumns(),
        gameData.getNumberOfRows(), gameData);
    applyNewGridValues(resetGrid);
  }

  /**
   * Gets the current game engine being used in the game.
   *
   * @return the game engine
   */
  public Engine getGameEngine() {
    return gameEngine;
  }

  /**
   * Gets the game configuration data for a game.
   *
   * @return the game configuration data
   */
  public GameData getGameData() {
    return gameData;
  }

  /**
   * Creates a string of the timer to be displayed in the view, given that the game is configured to
   * display a timer.
   *
   * @return the timer string
   */
  public String getTimerString() {
    if (gameData.isShowTimer()) {
      return "Timer: " + gameEngine.getGameTime();
    } else {
      return "";
    }
  }

  /**
   * Gets the current score of the game.
   *
   * @return score of the game
   */
  public int getCurrentScore() {
    return gameEngine.getGameScore();
  }

  private String checkCurrentCheatCode() throws InvalidParameterException {
    if (((Display) this.view).isCheatCodeUpdated()) {
      ((Display) this.view).setIsCheatCodeUpdated(false);
      String cheat = view.getCurrentCheatCode();
      if (validProperties.getString(CHEAT_CODES_KEY).contains(cheat)) {
        return cheat;
      } else {
        throw new InvalidParameterException(CHEAT_CODES_MESSAGE);
      }
    }

    return null;
  }

}

