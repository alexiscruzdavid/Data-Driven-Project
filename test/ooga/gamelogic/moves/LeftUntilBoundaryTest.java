package ooga.gamelogic.moves;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.boundary.EdgeBoundary;
import ooga.gamelogic.boundary.OtherEntityBoundary;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeftUntilBoundaryTest {

  private List<Agent> curEntity;
  private Grid grid;
  private List<Boundary> boundaries = List.of(new EdgeBoundary(), new OtherEntityBoundary());


  @BeforeEach
  public void setUp() {
    grid = new Grid(10, 10);
    grid.changeCell(new Coordinate(1, 2), 3);
    grid.changeCell(new Coordinate(0, 3), 3);
    grid.changeCell(new Coordinate(0, 2), 3);
    grid.updateCells();
    curEntity =
        List.of(new Agent(List.of(grid.getCellFromCoordinate(new Coordinate(1, 2)))),
            new Agent(List.of(grid.getCellFromCoordinate(new Coordinate(0, 1)))),
            new Agent(List.of(grid.getCellFromCoordinate(new Coordinate(0, 2)))));
  }

  @Test
  void moveActiveAgentLeftUntilBoundary() {
    int expected = 3;
    Move m = new LeftUntilBoundary(boundaries);
    m.moveEntity(curEntity, grid);
    int state = grid.getCellFromCoordinate(new Coordinate(0, 3)).getCurrentState();
    assertEquals(expected, state);
  }

  @Test
  void moveActiveAgentLeftCheckOpen() {
    int expected = 0;
    Move m = new LeftUntilBoundary(boundaries);
    m.moveEntity(curEntity, grid);
    int state = grid.getCellFromCoordinate(new Coordinate(1, 2)).getCurrentState();
    assertEquals(expected, state);
  }

  @Test
  void moveActiveAgentTwoTimes() {
    int expected = 3;
    Move m = new LeftUntilBoundary(boundaries);
    m.moveEntity(curEntity, grid);
    m.moveEntity(curEntity, grid);
    int state = grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState();
    assertEquals(expected, state);
  }
}