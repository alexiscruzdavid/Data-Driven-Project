package ooga.gamelogic.boundary;

import java.util.List;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;



/**
 * Implements the Boundary interface OtherEntityBoundary checks whether the agent passed has
 * collided with a cell that isn't empty, i.e. has collided with another entity
 */
public class OtherEntityBoundary implements Boundary {


  private static final String GET_NEIGHBOR_METHOD = "getDirect%sToBody";

  /**
   * Checks if the entity passed would be hitting another agent if it would move in a particular
   * direction.
   *
   * @param activeAgent the agent to check has collided with the boundary
   * @param grid        the grid in which the agent resides in
   * @param direction   the direction the move would occur in
   * @return a boolean specifying whether the agent will collide with another entity on the grid
   * @see Boundary
   */
  @Override
  public boolean isHittingBoundary(Entity activeAgent, Grid grid, String direction) {
    try {
      List<Coordinate> adjacentCoordinates = (List<Coordinate>) activeAgent.getClass()
          .getMethod(String.format(GET_NEIGHBOR_METHOD, direction)).invoke(activeAgent);
      for (Coordinate c : adjacentCoordinates) {
        if (isCoordinateInAgent(activeAgent, c)) {
          continue;
        }
        if (grid.isCellFull(c)) {
          return true;
        }
      }
    } catch (Exception e) {
      return true;
    }
    return false;
  }

  private Boolean isCoordinateInAgent(Entity activeAgent, Coordinate c) {
    for (Cell cell : activeAgent.getBody()) {
      if (cell.getCoordinates() == c) {
        return true;
      }
    }
    return false;
  }
}
