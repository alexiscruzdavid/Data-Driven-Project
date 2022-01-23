package ooga.gamelogic.destroyblocks;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;


/**
 * Implements the RemoveBlockLogic interface IncrementAndRemove increments the
 * state of the cell with which the activeAgent collides with if they share the same state.
 * Otherwise, both entities will remain on the grid as if nothing happened.
 */

public class GroupDestroy implements RemoveBlockLogic {

  private static final int EMPTY = 0;
  private static final int FIRST_CELL_IN_BODY = 0;
  private static final String UP_DIRECTION = "Up";
  private static final String DOWN_DIRECTION = "Down";
  private static final String LEFT_DIRECTION = "Left";
  private static final String RIGHT_DIRECTION = "Right";
  private static final String GET_NEIGHBOR_METHOD = "getDirect%sToBody";

  /**
   * This method removes blocks if they are a part of a group of three.
   *
   * @param agent     the Entity to potentially remove
   * @param grid      the grid in which the agent exists
   * @param direction the direction of the last move made
   */
  @Override
  public boolean removeBlock(Entity agent, Grid grid, String direction) {
    return foundGroupsToDestroy(grid, agent.getBody().get(FIRST_CELL_IN_BODY));
  }

  private boolean foundGroupsToDestroy(Grid grid, Cell c) {
    return c.getCurrentState() != EMPTY && (
        checkCellNeighbors(c, grid, LEFT_DIRECTION, RIGHT_DIRECTION)
            || checkCellNeighbors(c, grid, UP_DIRECTION, DOWN_DIRECTION));
  }

  private boolean checkCellNeighbors(Cell currentCell, Grid grid, String firstDirection,
      String secondDirection) {
    try {
      for (Coordinate firstCoordinate : (List<Coordinate>) currentCell.getClass()
          .getMethod(String.format(GET_NEIGHBOR_METHOD, firstDirection)).invoke(currentCell)) {
        for (Coordinate secondCoordinate : (List<Coordinate>) currentCell.getClass()
            .getMethod(String.format(GET_NEIGHBOR_METHOD, secondDirection)).invoke(currentCell)) {
          if (checkSameAndDestroy(currentCell, grid, firstCoordinate, secondCoordinate)) {
            return true;
          }
        }
      }
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      return false;
    }
    return false;
  }

  private boolean checkSameAndDestroy(Cell currentCell, Grid grid, Coordinate firstNeighbor,
      Coordinate secondNeighbor) {
    Cell firstAdjacent = grid.getCellFromCoordinate(firstNeighbor);
    Cell secondAdjacent = grid.getCellFromCoordinate(secondNeighbor);
    if (firstAdjacent != null && secondAdjacent != null
        && firstAdjacent.getCurrentState() == secondAdjacent.getCurrentState()
        && firstAdjacent.getCurrentState() == currentCell.getCurrentState()) {
      firstAdjacent.setNextState(EMPTY);
      secondAdjacent.setNextState(EMPTY);
      currentCell.setNextState(EMPTY);
      return true;
    }
    return false;
  }

}

