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

class UpSwapTest {

  private List<Agent> curEntity;
  private Grid grid;
  private List<Boundary> boundaries = List.of(new EdgeBoundary(), new OtherEntityBoundary());

  @BeforeEach
  void setUp() {
    grid = new Grid(10, 10);
    grid.changeCell(new Coordinate(1, 1), 5);
    grid.changeCell(new Coordinate(2, 1), 3);
    grid.updateCells();
    curEntity = List.of(new Agent(List.of(grid.getCell(2, 1))));

  }

  @Test
  void singleSwapTest() {
    int expectedSecondSpot = 3;
    int expectedFirstSpot = 5;
    Move m = new SwapUp(boundaries);
    m.moveEntity(curEntity, grid);
    int stateSecond = grid.getCellFromCoordinate(new Coordinate(1, 1)).getCurrentState();
    int stateFirst = grid.getCellFromCoordinate(new Coordinate(2, 1)).getCurrentState();
    assertEquals(expectedSecondSpot, stateSecond);
    assertEquals(expectedFirstSpot, stateFirst);
  }

  @Test
  void doubleSwapTest() {
    int expectedSecondSpot = 5;
    int expectedFirstSpot = 3;
    Move m = new SwapUp(boundaries);
    m.moveEntity(curEntity, grid);
    m.moveEntity(curEntity, grid);
    int stateSecond = grid.getCellFromCoordinate(new Coordinate(1, 1)).getCurrentState();
    int stateFirst = grid.getCellFromCoordinate(new Coordinate(2, 1)).getCurrentState();
    assertEquals(expectedSecondSpot, stateSecond);
    assertEquals(expectedFirstSpot, stateFirst);
  }

  @Test
  void threeCellsInAgentSwap() {
    curEntity = List.of(new Agent(List.of(grid.getCell(0, 1))),
        new Agent(List.of(grid.getCell(0, 2))), new Agent(List.of(grid.getCell(0, 3))));
    int expectedSecondSpot = 5;
    int expectedFirstSpot = 3;
    Move m = new SwapUp(boundaries);
    m.moveEntity(curEntity, grid);
    int stateSecond = grid.getCellFromCoordinate(new Coordinate(1, 1)).getCurrentState();
    int stateFirst = grid.getCellFromCoordinate(new Coordinate(2, 1)).getCurrentState();
    assertEquals(expectedSecondSpot, stateSecond);
    assertEquals(expectedFirstSpot, stateFirst);
  }
}