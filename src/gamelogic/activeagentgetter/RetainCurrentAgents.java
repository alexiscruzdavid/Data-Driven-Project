package ooga.gamelogic.activeagentgetter;

import java.util.List;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;

/**
 * Implements the interface ActiveAgentGetter, and provides the strategy of simply keeping the
 * previous active agents as the next active agent. This strategy is utilized when the method is
 * called.
 */
public class RetainCurrentAgents implements ActiveAgentGetter {

  /**
   * Returns the current active agents as the next active agents to use in the game.
   *
   * @param grid               the current grid in play
   * @param currentAgents      the current agents that exist before getting the new agents
   * @param latestAgentSpawned the latest agent that has been spawned in the game
   * @return the current active agents
   */
  @Override
  public List<Agent> getActiveAgent(Grid grid, List<Agent> currentAgents,
      Agent latestAgentSpawned) {
    return currentAgents;
  }
}
