package ooga.gamelogic.destroyblocks;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;


/**
 * Implements the RemoveBlockLogic interface IncrementAndRemove increments the state of the cell
 * with which the activeAgent collides with if they share the same state. Otherwise, both entities
 * will remain on the grid as if nothing happened.
 */
public class IncrementAndRemove implements RemoveBlockLogic {

  private static final String GET_NEIGHBOR_METHOD = "getDirect%sToBody";
  private static final int EMPTY = 0;
  private static final int SINGLE_INCREMENT = 1;

  /**
   * Removes a block when an adjacent block is the same state and increments the adjacent block.
   *
   * @param agent     the Entity to potentially remove
   * @param grid      the grid in which the agent exists
   * @param direction the direction of the last move made
   */
  @Override
  public boolean removeBlock(Entity agent, Grid grid, String direction) {
    try {
      for (Cell c : agent.getBody()) {
        for (Coordinate coordinate : (List<Coordinate>) c.getClass()
            .getMethod(String.format(GET_NEIGHBOR_METHOD, direction)).invoke(c)) {
          Cell adjacentCell = grid.getCellFromCoordinate(coordinate);
          if (adjacentCell != null && c.getCurrentState() == adjacentCell.getCurrentState()
              && adjacentCell.getCurrentState() != EMPTY) {
            adjacentCell.setNextState(adjacentCell.getCurrentState() + SINGLE_INCREMENT);
            adjacentCell.setCurrentState(adjacentCell.getCurrentState() + SINGLE_INCREMENT);
            c.setNextState(EMPTY);
            return true;
          }
        }
      }
    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
      return false;
    }
    return false;
  }

}
