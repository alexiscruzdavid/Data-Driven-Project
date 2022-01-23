package ooga.gamelogic.activeagentgetter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NewlySpawnedAgentTest {

  private NewlySpawnedAgent newlySpawnedAgent;
  private Grid grid;
  private List<Agent> activeAgents = List.of(new Agent());
  private Agent latestAgent;

  @BeforeEach
  void setup() {
    newlySpawnedAgent = new NewlySpawnedAgent();
    activeAgents = List.of(new Agent());
    latestAgent = new Agent();
  }

  @Test
  void getActiveAgentCheckSame() {

    latestAgent.addCell(new Cell(4, 4, 4));
    List<Agent> newAgent = newlySpawnedAgent.getActiveAgent(grid, activeAgents, latestAgent);
    assertEquals(newAgent.get(0), latestAgent);

  }

  @Test
  void getActiveAgentNoLatestAgent() {
    latestAgent = null;
    List<Agent> newAgent = newlySpawnedAgent.getActiveAgent(grid, activeAgents, latestAgent);
    assertEquals(List.of(), newAgent);
  }
}