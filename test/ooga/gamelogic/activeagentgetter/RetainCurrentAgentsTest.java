package ooga.gamelogic.activeagentgetter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RetainCurrentAgentsTest {

  private RetainCurrentAgents retainCurrentAgents;
  private Grid grid;
  private List<Agent> activeAgents = List.of(new Agent());
  private Agent latestAgent;

  @BeforeEach
  void setup() {
    retainCurrentAgents = new RetainCurrentAgents();
    activeAgents = List.of(new Agent());
    latestAgent = new Agent();
  }

  @Test
  void getActiveAgentCheckSame() {
    latestAgent.addCell(new Cell(4, 4, 4));
    Agent otherAgent = new Agent();
    otherAgent.addCell(new Cell(3, 3, 3));
    activeAgents = List.of(latestAgent, otherAgent);
    List<Agent> newAgent = retainCurrentAgents.getActiveAgent(grid, activeAgents, latestAgent);
    assertEquals(activeAgents, newAgent);

  }

  @Test
  void getActiveAgentNoActiveAgent() {
    activeAgents = null;
    List<Agent> newAgent = retainCurrentAgents.getActiveAgent(grid, activeAgents, latestAgent);
    assertEquals(null, newAgent);
  }
}