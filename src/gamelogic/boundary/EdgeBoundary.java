package ooga.gamelogic.boundary;

import java.util.List;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;

/**
 * Implements the Boundary interface EdgeBoundary checks whether the agent passed is currently along
 * some edge or beyond the edges of the passed grid.
 */
public class EdgeBoundary implements Boundary {

  private static final String GET_NEIGHBOR_METHOD = "getDirect%sToBody";
  private static final int STARTING_ROW = 0;
  private static final int STARTING_COL = 0;

  /**
   * Determines if the current active agent is hitting the boundary.
   *
   * @param activeAgent the agent to check has collided with the boundary
   * @param grid        the grid in which the agent resides in
   * @param direction   the direction in which we are checking for a boundary
   * @return a boolean specifying whether the agent is sitting along some edge or exists beyond the
   *        edges of the grid.
   */

  @Override
  public boolean isHittingBoundary(Entity activeAgent, Grid grid, String direction) {
    try {
      for (Coordinate c : (List<Coordinate>) activeAgent.getClass()
          .getMethod(String.format(GET_NEIGHBOR_METHOD, direction)).invoke(activeAgent)) {
        if (isOutOfBounds(grid, c)) {
          return true;
        }
      }
    } catch (Exception e) {
      return true;
    }
    return false;
  }

  private boolean isOutOfBounds(Grid grid, Coordinate c) {
    return c.getRow() < STARTING_ROW || c.getRow() >= grid.getNumberOfRows()
        || c.getCol() < STARTING_COL || c.getCol() >= grid.getNumberOfColumns();
  }

}
