package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;

/**
 * Superclass for all moves that move the object until a boundary, rather than just a single cell on
 * the board.
 */
public abstract class MoveUntilBoundary extends Move {

  /**
   * Constructor for the MoveUntilBoundary superclass, sets the correct boundary checks we want to
   * apply for all moves.
   *
   * @param boundaries the boundary checks we want to apply for all moves
   */
  public MoveUntilBoundary(List<Boundary> boundaries) {
    super(boundaries);
  }

  /**
   * Overrides the moveEntity functionality to repeat until hits a boundary.
   *
   * @param activeAgents all the active agents that we want to move on the grid
   * @param currentGrid  the grid that contains the current cells and agents
   */
  @Override
  public void moveEntity(List<Agent> activeAgents, Grid currentGrid) {
    while (isRedoMove(activeAgents, currentGrid)) {
      super.moveEntity(activeAgents, currentGrid);
    }
  }

  /**
   * Method to determine if we can redo the move without hitting a boundary for any of the agents.
   *
   * @param activeAgents the active agents that we want to see if will hit a boundary
   * @param grid         the grid that contains all the current cells and agents
   * @return a boolean indicating if any of the agents can move again without hitting a boundary
   */
  private boolean isRedoMove(List<Agent> activeAgents, Grid grid) {
    for (Agent agent : activeAgents) {
      if (!willHitBoundary(agent, grid)) {
        return true;
      }
    }
    return false;
  }
}
