package ooga.gamelogic.destroyblocks;

import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;

/**
 * An interface outlining the logic for removing cells during game updates.
 */
public interface RemoveBlockLogic {

  /**
   * checks if the passed agent has met the conditions to be removed and is subsequently removed.
   *
   * @param agent     the Entity to potentially remove
   * @param grid      the grid in which the agent exists
   * @param direction the direction of the last move made
   */
  boolean removeBlock(Entity agent, Grid grid, String direction);
}
