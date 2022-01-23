package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;


/**
 * Extends move superclass to implement the swap functionality. Takes in an active agent and
 * switches it with another cell, and subclasses implement which direction it is swapped with.
 */
public abstract class Swap extends Move {

  private static final int FIRST_CELL_IN_BODY = 0;
  private static final int ONE_AGENT = 1;
  /**
   * Constructor for Swap, just implements the same functionality as the move constructor.
   *
   * @param boundaries boundaries for the swap
   */
  protected Swap(List<Boundary> boundaries) {
    super(boundaries);
  }

  /**
   * Overrides moveEntity in Move superclass to implement swapping the two cells.
   *
   * @param activeAgents the list of active agents that we want to potentially swap
   * @param currentGrid  the grid that contains the current cells and agents
   */
  @Override
  public void moveEntity(List<Agent> activeAgents, Grid currentGrid) {
    if (activeAgents.size() == ONE_AGENT) {
      Agent currentAgent = activeAgents.get(FIRST_CELL_IN_BODY);
      Coordinate firstSpot = currentAgent.getBody().get(FIRST_CELL_IN_BODY).getCoordinates();
      Coordinate secondSpot = getLocationToSwapWith(currentAgent);
      currentGrid.swapCellInGrid(firstSpot, secondSpot);
      currentGrid.updateCells();

    }
  }

  /**
   * Abstract method to get the location to swap with, subclasses override with coordinates in
   * desired direction.
   *
   * @param currentAgent agent we want to swap in a particular direction
   * @return the coordinates to swap the currentAgent into
   */
  public abstract Coordinate getLocationToSwapWith(Agent currentAgent);

}


