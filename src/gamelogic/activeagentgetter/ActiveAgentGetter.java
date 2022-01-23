package ooga.gamelogic.activeagentgetter;

import java.util.List;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;


/**
 * This interface defines the method used to retrieve the active agents for a particular game. Each
 * class that implements this interface implements this method in a different way, thus providing
 * different strategies for which to obtain active agents.
 */
public interface ActiveAgentGetter {

  /**
   * This method is used to retrieve the current active agents for the game. It can be implemented
   * in many different ways to retrieve the desired active agents for a particular application.
   *
   * @param grid the current grid in play
   * @param currentAgents the current agents that exist before getting the new agents
   * @param latestAgentSpawned the latest agent that has been spawned in the game
   * @return the new active agents that are in play for the game
   */
  List<Agent> getActiveAgent(Grid grid, List<Agent> currentAgents, Agent latestAgentSpawned);
}
