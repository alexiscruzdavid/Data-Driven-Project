package ooga.gamelogic.boundary;

import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;

/**
 * An interface outlining the logic for Boundary checking during moves.
 */
public interface Boundary {

  /**
   * checks if the entity passed through is touching/colliding with the specific boundary. checks if
   * the boundary conditions (whatever they are) are true.
   *
   * @param activeAgent the agent to check has collided with the boundary
   * @param grid        the grid in which the agent resides in
   * @param direction   the direction we want to check the boundary in
   * @return a boolean specifying whether the boundary conditions were true and thus the agent is
   *         'hitting' a certain obstacle
   */
  boolean isHittingBoundary(Entity activeAgent, Grid grid, String direction);
}
