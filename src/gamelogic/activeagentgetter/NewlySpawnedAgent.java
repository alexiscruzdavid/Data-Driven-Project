package ooga.gamelogic.activeagentgetter;

import java.util.List;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;


/**
 * Implements the interface ActiveAgentGetter, and provides the strategy of utilizing the newly
 * spawned agent as the next active agent. This strategy is utilized when the method is called.
 */
public class NewlySpawnedAgent implements ActiveAgentGetter {

  /**
   * Gets the latest spawned agent as the next active agent.
   *
   * @param grid               the current grid in play
   * @param currentAgents      the current agents that exist before getting the new agents
   * @param latestAgentSpawned the latest agent that has been spawned in the game
   * @return the latest spawned agent
   */
  @Override
  public List<Agent> getActiveAgent(Grid grid, List<Agent> currentAgents,
      Agent latestAgentSpawned) {
    if (latestAgentSpawned != null) {
      return List.of(latestAgentSpawned);
    }
    return List.of();
  }
}
