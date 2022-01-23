package ooga.gamelogic.destroyblocks;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;


/**
 * Implements the RemoveBlockLogic interface FullLine removes any agent that is in a full row of
 * nonempty cells.
 */
public class FullRow implements RemoveBlockLogic {

  private static final int EMPTY = 0;

  /**
   * Removes a full row of cells if all cells in that row are not empty.
   *
   * @param agent     the Entity to potentially remove
   * @param grid      the grid in which the agent exists
   * @param direction the direction of the last move made
   */
  @Override
  public boolean removeBlock(Entity agent, Grid grid, String direction) {
    for (Cell c : agent.getBody()) {
      List<Integer> rowStates = new LinkedList<>();
      grid.getRowOfCells(c.getCoordinates().getRow())
          .forEach(cell -> rowStates.add(cell.getCurrentState()));
      if (Collections.frequency(rowStates, 0) <= EMPTY) {
        grid.getRowOfCells(c.getCoordinates().getRow()).forEach(cell -> cell.setNextState(EMPTY));
        return true;
      }
    }
    return false;
  }
}
