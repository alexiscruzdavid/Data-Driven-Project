package ooga.gamelogic.endgame;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.timekeeper.TimeKeeper;

/**
 * Ends game when the timekeeper reaches zero.
 *
 * @author Kyle White
 */
public class TimeOut implements EndGameLogic {

  private static final int ZERO_TIME = 0;
  /**
   * Checks conditions, which in this case entails ensuring that there is still time remaining in
   * the game.
   *
   * @param currentAgent the current agent in the game
   * @param currentGrid  the current grid the game is existing on
   * @param timer        the timer keeping time for the game
   * @return a boolean indicating if time has run out for the game
   */
  @Override
  public boolean checkConditions(Agent currentAgent, Grid currentGrid, TimeKeeper timer) {
    if (timer.getTime() > ZERO_TIME) {
      return false;
    }
    return true;
  }

}
