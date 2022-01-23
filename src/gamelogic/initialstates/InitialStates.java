package ooga.gamelogic.initialstates;

import ooga.data.GameData;
import ooga.gamelogic.grid.Grid;

/**
 * This interface defines the method used to generate the initial states for a particular game. Each
 * class that implements this interface implements this method in a different way, thus providing
 * different strategies for which to generate initial states.
 */
public interface InitialStates {

  /**
   * Generates the initial states for a game based on the passed grid parameters. Different classes
   * implement this method differently, which allows for initial states to be generated in many
   * different ways.
   *
   * @param gridWidth      the width of the grid being created
   * @param gridHeight     the height of the grid being created
   * @param gameParameters the game configuration data passed by the user
   * @return a grid of the correct initial states
   */
  public Grid generateInitialState(int gridWidth, int gridHeight, GameData gameParameters);

}
