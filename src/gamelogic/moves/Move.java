package ooga.gamelogic.moves;

import java.util.ArrayList;
import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;

/**
 * Superclass that represents changing the location of the object on the grid. This is accomplished
 * by moving the list of agents their current coordinates to their new coordinates. Subclasses
 * extend the class to create specific moves in certain directions by providing the correct new
 * coordinates for a move in a particular direction. Applies boundary checks to ensure moves are
 * valid.
 */
public abstract class Move {

  private static final int EMPTY = 0;

  private List<Boundary> boundaryChecks;
  private String direction;

  /**
   * Constructor for the move superclass, sets the correct boundary checks we want to apply for all
   * moves.
   *
   * @param boundaries the boundary checks we want to apply for all moves
   */
  public Move(List<Boundary> boundaries) {
    this.boundaryChecks = boundaries;
  }

  /**
   * Method to set the direction we want the object to move in.
   *
   * @param direction the direction we want to move the object in
   */
  protected void setDirection(String direction) {
    this.direction = direction;
  }

  /**
   * Method to move all the active agents in the list in the desired direction.
   *
   * @param activeAgents all the active agents that we want to move on the grid
   * @param currentGrid  the grid that contains the current cells and agents
   */
  public void moveEntity(List<Agent> activeAgents, Grid currentGrid) {
    for (Agent agent : activeAgents) {
      moveAgent(currentGrid, agent);
    }
  }

  /**
   * Method to move a particular agent within an entity, checks if will hit the boundary, then
   * executes moving the cells in the grid and updating the agent.
   *
   * @param currentGrid the current grid to move the agent on
   * @param agent the agent we want to move
   */
  public void moveAgent(Grid currentGrid, Agent agent) {
    if (!willHitBoundary(agent, currentGrid)) {
      List<Cell> newCells = new ArrayList<>();
      setAllNextStatesToEmpty(agent);
      moveCellInGrid(currentGrid, newCells, agent);
      agent.update(newCells);
      currentGrid.updateCells();
    }
  }

  /**
   * Method to determine if moving the agent will violate the boundary checks.
   *
   * @param agent  the agent currently moving
   * @param myGrid the grid that contains all the current cells and agents
   * @return a boolean indicating whether the agent will hit the boundary
   */
  protected boolean willHitBoundary(Agent agent, Grid myGrid) {
    for (Boundary b : boundaryChecks) {
      if (b.isHittingBoundary(agent, myGrid, direction)) {
        return true;
      }
    }
    return false;
  }

  private void moveCellInGrid(Grid currentGrid, List<Cell> newCells, Agent agent) {
    for (Cell c : agent.getBody()) {
      if (c != null) {
        Coordinate newLocation = getNewCoordinate(c);
        if (currentGrid.moveCellInGrid(c.getCoordinates(), newLocation)) {
          newCells.add(currentGrid.getCellFromCoordinate(newLocation));
        }
      }
    }
  }

  private void setAllNextStatesToEmpty(Agent agent) {
    for (Cell c : agent.getBody()) {
      if (c != null) {
        c.setNextState(EMPTY);
      }
    }
  }

  /**
   * Determine the next coordinate for the cell to move to. The default is retaining the same
   * coordinates as before.
   *
   * @param c the cell we are looking to move to a new location
   * @return the new coordinates we would like to move the cell to
   */
  public Coordinate getNewCoordinate(Cell c) {
    return c.getCoordinates();
  }
}
