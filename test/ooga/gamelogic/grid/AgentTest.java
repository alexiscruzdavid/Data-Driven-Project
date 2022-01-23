package ooga.gamelogic.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class AgentTest {

  @Test
  void changeBodyTest() {
    Agent agent = new Agent();
    agent.addCell(new Cell(0, 0, 0));
    assertEquals(1, agent.getBody().size());
    agent.addCell(new Cell(0, 0, 0));
    assertEquals(1, agent.getBody().size());
    for (int i = 0; i < 10; i++) {
      agent.addCell(new Cell(0, 0, i));
    }
    assertEquals(10, agent.getBody().size());
  }

  @Test
  void createRowAgent() {
    Agent agent = new Agent(1, 1, 10, 5);
    assertEquals(10, agent.getBody().size());
    assertEquals(5, agent.getBody().get(0).getCurrentState());
    assertEquals(new Coordinate(1, 1), agent.getBody().get(0).getCoordinates());
  }

  @Test
  void updateWithNewCells() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5), new Cell(1, 2, 5), new Cell(1, 3, 5)));
    agent.update(List.of(new Cell(1, 0, 3), new Cell(1, 3, 3), new Cell(1, 4, 3)));
    assertEquals(3, agent.getBody().size());
    assertEquals(3, agent.getBody().get(0).getCurrentState());
    assertEquals(4, agent.getBody().get(2).getCoordinates().getCol());
  }

  @Test
  void updateWithEmptyList() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5), new Cell(1, 2, 5), new Cell(1, 3, 5)));
    agent.update(List.of());
    assertEquals(0, agent.getBody().size());
  }

  @Test
  void getDirectLeftToAgent() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5)));
    List<Coordinate> directLeftCoordinates = agent.getDirectLeftToBody();
    assertEquals(0, directLeftCoordinates.get(0).getCol());
    assertEquals(1, directLeftCoordinates.size());
  }

  @Test
  void getDirectLeftToAgentMultipleCells() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5), new Cell(2, 1, 5)));
    List<Coordinate> directLeftCoordinates = agent.getDirectLeftToBody();
    assertEquals(0, directLeftCoordinates.get(0).getCol());
    assertEquals(2, directLeftCoordinates.size());
  }

  @Test
  void getDirectRightToAgent() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5)));
    List<Coordinate> directRightCoordinates = agent.getDirectRightToBody();
    assertEquals(2, directRightCoordinates.get(0).getCol());
    assertEquals(1, directRightCoordinates.size());
  }

  @Test
  void getDirectRightToAgentMultipleCells() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5), new Cell(1, 2, 5)));
    List<Coordinate> directRightCoordinates = agent.getDirectRightToBody();
    assertEquals(3, directRightCoordinates.get(0).getCol());
    assertEquals(1, directRightCoordinates.size());
  }

  @Test
  void getDirectUpToAgentMultipleCells() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5), new Cell(1, 2, 5)));
    List<Coordinate> directUpCoordinates = agent.getDirectUpToBody();
    assertEquals(0, directUpCoordinates.get(0).getRow());
    assertEquals(2, directUpCoordinates.size());
  }

  @Test
  void getDirectUpToAgentOneCell() {
    Agent agent = new Agent(List.of(new Cell(6, 1, 5)));
    List<Coordinate> directUpCoordinates = agent.getDirectUpToBody();
    assertEquals(5, directUpCoordinates.get(0).getRow());
    assertEquals(1, directUpCoordinates.get(0).getCol());
    assertEquals(1, directUpCoordinates.size());
  }

  @Test
  void getDirectDownToAgentMultipleCells() {
    Agent agent = new Agent(List.of(new Cell(1, 1, 5), new Cell(2, 1, 5)));
    List<Coordinate> directDownCoordinates = agent.getDirectDownToBody();
    assertEquals(3, directDownCoordinates.get(0).getRow());
    assertEquals(1, directDownCoordinates.size());
  }

  @Test
  void getDirectDownToAgentOneCell() {
    Agent agent = new Agent(List.of(new Cell(6, 1, 5)));
    List<Coordinate> directDownCoordinates = agent.getDirectDownToBody();
    assertEquals(7, directDownCoordinates.get(0).getRow());
    assertEquals(1, directDownCoordinates.get(0).getCol());
    assertEquals(1, directDownCoordinates.size());
  }

  @Test
  void generateRandomBodyTwoCells() {
    Agent agent = new Agent();
    agent.setRandomSeed(7);
    agent.generateRandomBody(4, 4, 7, 2);
    assertEquals(7, agent.getBody().get(0).getCurrentState());
    assertEquals(4, agent.getBody().get(0).getCoordinates().getCol());
    assertEquals(2, agent.getBody().size());
    assertEquals(6, agent.getBody().get(1).getCoordinates().getRow());
  }

  @Test
  void generateRandomBodyFourCells() {
    Agent agent = new Agent();
    agent.setRandomSeed(7);
    agent.generateRandomBody(5, 1, 7, 4);
    assertEquals(7, agent.getBody().get(0).getCurrentState());
    assertEquals(1, agent.getBody().get(0).getCoordinates().getCol());
    assertEquals(4, agent.getBody().size());

  }

}