package ooga.gamelogic.moves;

import java.util.ArrayList;
import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;


/**
 * Extends the Move superclass, rotates the agent left if possible.
 */
public class RotateAgentLeft extends Move {

  private static final List<String> allDirections = new ArrayList(
      List.of("Left", "Right", "Up", "Down"));
  private static final int HALF_OF_AGENT = 2;
  private Agent activeAgent;

  /**
   * Constructor for RotateAgentLeft, takes in the boundaries we would like to apply to the agent
   * when it is attempting to rotate.
   *
   * @param boundaries the boundaries used to check if the agent can rotate
   */
  public RotateAgentLeft(List<Boundary> boundaries) {
    super(boundaries);
  }

  /**
   * Overrides the move agent functionality to set the active agent in the move before calling the
   * move method in the superclass.
   *
   * @param currentGrid the current grid to move the agent on
   * @param agent       the agent we want to move
   */
  @Override
  public void moveAgent(Grid currentGrid, Agent agent) {
    activeAgent = agent;
    super.moveAgent(currentGrid, agent);
  }

  /**
   * Overrides method in the superclass' functionality to check in all directions if the agent would
   * hit a boundary, rather than just one.
   *
   * @param agent       the agent currently moving
   * @param currentGrid the grid the agent is trying to rotate on
   * @return a boolean indicating whether the agent will hit a boundary upon rotation
   */
  @Override
  public boolean willHitBoundary(Agent agent, Grid currentGrid) {
    for (String direction : allDirections) {
      setDirection(direction);
      if (super.willHitBoundary(agent, currentGrid)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Overrides the method to get a new coordinate by applying rotation logic to determine the new
   * coordinate for each cell.
   *
   * @param c the cell we are looking to move to a new location
   * @return the coordinate for the new spot for the cell
   */
  @Override
  public Coordinate getNewCoordinate(Cell c) {
    int centerCell = activeAgent.getBody().size() / HALF_OF_AGENT;
    Coordinate centerCoordinate = activeAgent.getBody().get(centerCell).getCoordinates();
    int oldDistR = -centerCoordinate.getRow() + c.getCoordinates().getRow();
    int oldDistC = -centerCoordinate.getCol() + c.getCoordinates().getCol();
    int newR = c.getCoordinates().getRow() - oldDistR + oldDistC;
    int newC = c.getCoordinates().getCol() - oldDistR - oldDistC;
    return new Coordinate(newR, newC);
  }
}
