package ooga.gamelogic.destroyblocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupDestroyTest {

  int gridWidth = 10;
  int gridHeight = 10;
  GroupDestroy removeCondition;
  Cell cell1;
  Cell cell2;
  Cell cell3;
  Grid grid;

  @BeforeEach
  void setup() {
    removeCondition = new GroupDestroy();
    cell1 = new Cell(0, 0, 1);
    cell2 = new Cell(1, 0, 1);
    cell3 = new Cell(2, 0, 1);
    grid = new Grid(gridWidth, gridHeight);
    grid.addEntity(cell1);
    grid.addEntity(cell2);
    grid.addEntity(cell3);
  }

  @Test
  void removeCellInGroupOf3Column() {
    for (Cell c : grid.getCellsInGrid()) {
      removeCondition.removeBlock(c, grid, "None");
    }
    grid.updateCells();
    assertEquals(0, grid.getCellFromCoordinate((cell1).getCoordinates()).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate((cell2).getCoordinates()).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate((cell3).getCoordinates()).getCurrentState());
  }


  @Test
  void removeCellOnlyTwo() {
    grid.getCell(2, 0).setCurrentState(9);
    grid.getCell(2, 0).setNextState(9);
    for (Cell c : grid.getCellsInGrid()) {
      removeCondition.removeBlock(c, grid, "None");
    }
    grid.updateCells();
    assertEquals(1, grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState());
    assertEquals(1, grid.getCellFromCoordinate(new Coordinate(1, 0)).getCurrentState());
    assertEquals(9, grid.getCellFromCoordinate(new Coordinate(2, 0)).getCurrentState(),
        "The cell was not removed");
  }

  @Test
  void removeCellInGroupOf3Row() {
    cell1 = new Cell(0, 1, 1);
    cell2 = new Cell(0, 2, 1);
    cell3 = new Cell(0, 3, 1);
    grid.addEntity(cell1);
    grid.addEntity(cell2);
    grid.addEntity(cell3);
    grid.updateCells();
    for (Cell c : grid.getCellsInGrid()) {
      removeCondition.removeBlock(c, grid, "None");
    }
    grid.updateCells();
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(0, 1)).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(0, 2)).getCurrentState());
  }

  @Test
  void removeCellManyGroups() {
    cell1 = new Cell(5, 1, 1);
    cell2 = new Cell(5, 2, 1);
    cell3 = new Cell(5, 3, 1);
    grid.addEntity(cell1);
    grid.addEntity(cell2);
    grid.addEntity(cell3);
    grid.updateCells();

    for (Cell c : grid.getCellsInGrid()) {
      removeCondition.removeBlock(c, grid, "None");
    }
    grid.updateCells();
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(5, 3)).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(5, 1)).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(5, 2)).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(1, 0)).getCurrentState());
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(2, 0)).getCurrentState());
  }
}