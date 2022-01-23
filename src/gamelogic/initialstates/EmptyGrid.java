package ooga.gamelogic.initialstates;


import ooga.data.GameData;
import ooga.gamelogic.grid.Grid;

/**
 * Class that implements the particular logic for generating an empty grid as the starting states
 * for the game.
 */
public class EmptyGrid implements InitialStates {

  /**
   * Produces an empty grid to be used as the initial states for the game.
   *
   * @param gridWidth      the width of the initial grid
   * @param gridHeight     the height of the initial grid
   * @param gameParameters gameData that contains game configuration information
   * @return a grid of the correct dimensions filled with all empty initial states
   */
  @Override
  public Grid generateInitialState(int gridWidth, int gridHeight, GameData gameParameters) {
    return new Grid(gridWidth, gridHeight);
  }
}
