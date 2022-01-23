package ooga.gamelogic.endgame;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.timekeeper.TimeKeeper;


/**
 * Interface for the logic of the end game.
 *
 * @author Kyle White
 */
public interface EndGameLogic {

  /**
   * Checks conditions for ending the game.
   *
   * @param currentAgent the current agent in use
   * @param currentGrid  the current grid
   * @param timer        the game timer
   * @return true/false indicating if game end conditions have been met
   */
  public boolean checkConditions(Agent currentAgent, Grid currentGrid,
      TimeKeeper timer);

}
