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

class RotateAgentLeftTest {

  private List<Agent> curEntity;
  private Grid grid;
  private List<Boundary> boundaries = List.of(new EdgeBoundary(), new OtherEntityBoundary());


  @BeforeEach
  public void setUp() {
    grid = new Grid(10, 10);
    grid.changeCell(new Coordinate(1, 2), 3);
    grid.changeCell(new Coordinate(1, 3), 3);
    grid.changeCell(new Coordinate(1, 4), 3);
    grid.updateCells();
    curEntity =
        List.of(new Agent(List.of(grid.getCellFromCoordinate(new Coordinate(1, 2)),
            grid.getCellFromCoordinate(new Coordinate(1, 3)),
            grid.getCellFromCoordinate(new Coordinate(1, 4)))));
  }

  @Test
  void rotateLineOfCells() {
    int expected = 3;
    Move m = new RotateAgentLeft(boundaries);
    m.moveEntity(curEntity, grid);
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(0, 3)).getCurrentState());
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(1, 3)).getCurrentState());
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(2, 3)).getCurrentState());
  }

  @Test
  void rotateLineOfCellsIntoWall() {
    grid.changeCell(new Coordinate(0, 0), 3);
    grid.changeCell(new Coordinate(1, 0), 3);
    grid.changeCell(new Coordinate(2, 0), 3);
    grid.updateCells();
    curEntity =
        List.of(new Agent(List.of(grid.getCellFromCoordinate(new Coordinate(0, 0)),
            grid.getCellFromCoordinate(new Coordinate(1, 0)),
            grid.getCellFromCoordinate(new Coordinate(2, 0)))));
    Move m = new RotateAgentLeft(boundaries);
    m.moveEntity(curEntity, grid);
    grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState();
    assertEquals(3, grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState());
    assertEquals(3, grid.getCellFromCoordinate(new Coordinate(1, 0)).getCurrentState());
    assertEquals(3, grid.getCellFromCoordinate(new Coordinate(2, 0)).getCurrentState());
  }

  @Test
  void rotateActiveAgentTwoTimes() {
    int expected = 3;
    Move m = new RotateAgentLeft(boundaries);
    m.moveEntity(curEntity, grid);
    m.moveEntity(curEntity, grid);
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(0, 3)).getCurrentState());
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(1, 3)).getCurrentState());
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(2, 3)).getCurrentState());
  }

  @Test
  void rotateActiveAgentCheckEmptySpots() {
    int expected = 0;
    Move m = new RotateAgentLeft(boundaries);
    m.moveEntity(curEntity, grid);
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(1, 2)).getCurrentState());
    assertEquals(expected, grid.getCellFromCoordinate(new Coordinate(1, 4)).getCurrentState());
  }
}