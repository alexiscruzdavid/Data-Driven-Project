package ooga.gamelogic.endgame;

import java.util.ArrayList;
import java.util.List;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.timekeeper.TimeKeeper;

/**
 * Ends game when there is an overflow in the grid.
 *
 * @author Kyle White
 */
public class Overflow implements EndGameLogic {

  private static final int EMPTY = 0;

  private List<Coordinate> getOverflow(Grid grid) {
    List<Coordinate> overflow = new ArrayList<>();
    for (int i = 0; i < grid.getNumberOfColumns(); i++) {
      overflow.add(new Coordinate(0, i));
    }
    return overflow;
  }

  /**
   * Checks the conditions to see if there has been overflow in the grid (cells are touching the top
   * row).
   *
   * @param currentAgent the current agent in use
   * @param currentGrid  the current grid
   * @param timer        the game timer
   * @return whether overflow has occurred
   */
  @Override
  public boolean checkConditions(Agent currentAgent, Grid currentGrid,
      TimeKeeper timer) {
    for (Coordinate coordinate : getOverflow(currentGrid)) {
      Cell currentCell = currentGrid.getCellFromCoordinate(coordinate);
      if (currentCell.getCurrentState() != EMPTY && currentAgent != null && !currentAgent.getBody()
          .contains(currentCell)) {
        return true;
      }
    }
    return false;
  }
}
