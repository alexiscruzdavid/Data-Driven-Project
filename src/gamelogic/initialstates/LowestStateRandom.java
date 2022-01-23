package ooga.gamelogic.initialstates;

import java.util.Random;
import ooga.gamelogic.grid.Grid;
import ooga.data.GameData;

/**
 * Class that implements the particular logic for generating a grid with a certain number of blocks
 * filled with random states as the starting states for the game.
 */
public class LowestStateRandom implements InitialStates {

  private static final int EMPTY = 0;
  private static final int LOWEST_STATE = 1;
  private static final int NUM_CELLS_TO_GENERATE = 2;


  private Random myRandom = new Random();
  private int rowLocation;
  private int colLocation;

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
  public Grid generateInitialState(int gridWidth, int gridHeight,
      GameData gameParameters) {
    int[][] initialStates = new int[gridWidth][gridHeight];
    for (int i = 0; i < NUM_CELLS_TO_GENERATE; i++) {
      getRandomRowAndColumn(gridWidth, gridHeight);
      while (initialStates[rowLocation][colLocation] != EMPTY) {
        getRandomRowAndColumn(gridWidth, gridHeight);
      }
      initialStates[rowLocation][colLocation] = LOWEST_STATE;

    }
    return new Grid(initialStates);
  }

  private void getRandomRowAndColumn(int gridWidth, int gridHeight) {
    rowLocation = myRandom.nextInt(gridHeight);
    colLocation = myRandom.nextInt(gridWidth);
  }


  protected void setRandomSeed(int seed) {
    myRandom.setSeed(seed);
  }
}
