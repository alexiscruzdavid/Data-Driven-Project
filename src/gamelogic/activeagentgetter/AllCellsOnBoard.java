package ooga.gamelogic.activeagentgetter;


import java.util.LinkedList;
import java.util.List;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;


/**
 * Implements the interface ActiveAgentGetter, and provides the strategy of getting all the
 * non-empty cells on the board to be active agents. This strategy is utilized when the method is
 * called.
 */
public class AllCellsOnBoard implements ActiveAgentGetter {

  private static final int EMPTY = 0;

  /**
   * Gets all the cells that are "full" on the board as the next active agents for the game.
   *
   * @param grid          the current grid in play
   * @param currentAgents the current agents that exist before getting the new agents
   * @param latestAgent   the latest agent that has been spawned in the game
   * @return all cells on the board that are not empty
   */
  @Override
  public List<Agent> getActiveAgent(Grid grid, List<Agent> currentAgents, Agent latestAgent) {
    List<Cell> activeAgentBody = grid.getCellsInGrid();
    activeAgentBody.removeIf(cell -> cell.getCurrentState() == EMPTY);
    List<Agent> activeAgents = new LinkedList<>();
    activeAgentBody.forEach(cell -> activeAgents.add(new Agent(List.of(cell))));
    return activeAgents;
  }
}
