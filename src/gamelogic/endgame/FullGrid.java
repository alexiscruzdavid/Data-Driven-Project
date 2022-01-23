package ooga.gamelogic.endgame;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.timekeeper.TimeKeeper;

/**
 * Ends game when there is a full grid.
 *
 * @author Kyle White
 */
public class FullGrid implements EndGameLogic {

  private static final int EMPTY = 0;

  /**
   * Checks the grid to see if all the cells have a state greater than zero to determine if a game
   * is over.
   *
   * @param currentAgent the current agent in use
   * @param currentGrid  the current grid
   * @param timer        the game timer
   * @return boolean indicating if the grid is full and the game is over
   */
  @Override
  public boolean checkConditions(Agent currentAgent, Grid currentGrid, TimeKeeper timer) {
    for (Integer state : currentGrid) {
      if (state == EMPTY) {
        return false;
      }
    }
    return true;
  }

}
