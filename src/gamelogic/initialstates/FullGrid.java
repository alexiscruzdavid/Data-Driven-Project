package ooga.gamelogic.initialstates;

import java.util.Random;
import ooga.data.GameData;
import ooga.gamelogic.grid.Grid;

/**
 * Class that implements the particular logic for generating a full grid of random states as the
 * starting states for the game.
 */
public class FullGrid implements InitialStates {

  private static final int INCREMENT_BY_ONE = 1;
  private Random myRandom = new Random();

  /**
   * Generates the initial states for a game by randomly assigning each cell in the grid a
   * (non-empty) state within the allowed range of states.
   *
   * @param gridWidth      the width of the grid being created
   * @param gridHeight     the height of the grid being created
   * @param gameParameters the game configuration data passed by the user
   * @return a grid of the correct initial states
   */
  @Override
  public Grid generateInitialState(int gridWidth, int gridHeight, GameData gameParameters) {
    int[][] initialStates = new int[gridWidth][gridHeight];
    int maxState = gameParameters.getNumberOfStates();
    for (int i = 0; i < gridHeight; i++) {
      for (int j = 0; j < gridWidth; j++) {
        initialStates[i][j] = myRandom.nextInt(maxState) + INCREMENT_BY_ONE;
      }
    }
    return new Grid(initialStates);
  }

  protected void setRandomSeed(int seed) {
    myRandom.setSeed(seed);
  }
}
