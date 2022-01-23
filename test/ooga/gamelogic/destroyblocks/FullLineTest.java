package ooga.gamelogic.destroyblocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FullLineTest {

  int gridWidth = 10;
  int gridHeight = 10;
  FullRow removeCondition;
  Entity agent;
  Entity cell;
  Grid grid;

  @BeforeEach
  void setup() {
    removeCondition = new FullRow();
    agent = new Agent(1, 0, 10, 1);
    cell = new Cell(0, 0, 1);
    grid = new Grid(gridWidth, gridHeight);
    grid.addEntity(cell);
    grid.addEntity(agent);
  }

  @Test
  void removeCellInEmptyLine() {
    removeCondition.removeBlock(cell, grid, "None");
    grid.updateCells();
    assertEquals(1, grid.getCellFromCoordinate(((Cell) cell).getCoordinates()).getCurrentState(),
        "The cell was not removed");
  }


  @Test
  void removeAgentInFullLine() {
    removeCondition.removeBlock(agent, grid, "None");
    grid.updateCells();
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(1, 0)).getCurrentState(),
        "The cell was not removed");
  }

  @Test
  void dontRemoveNonFullLine() {
    grid.updateCells();
    removeCondition.removeBlock(cell, grid, "None");
    grid.updateCells();
    assertEquals(1, grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState(),
        "The cell was not removed");

  }

}