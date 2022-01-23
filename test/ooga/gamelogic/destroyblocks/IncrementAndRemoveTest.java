package ooga.gamelogic.destroyblocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncrementAndRemoveTest {

  int gridWidth = 10;
  int gridHeight = 10;
  IncrementAndRemove removeCondition;
  Entity agent;
  Entity cell;
  Grid grid;

  @BeforeEach
  void setup() {
    removeCondition = new IncrementAndRemove();
    agent = new Agent(1, 0, 10, 1);
    cell = new Cell(0, 0, 1);
    grid = new Grid(gridWidth, gridHeight);
    grid.addEntity(cell);
    grid.addEntity(agent);
  }

  @Test
  void incrementBlockInDownDirection1() {
    removeCondition.removeBlock(cell, grid, "Down");
    grid.updateCells();
    assertEquals(0, grid.getCellFromCoordinate(new Coordinate(0, 0)).getCurrentState(),
        "The cell was not removed");
  }

  @Test
  void incrementBlockInDownDirection2() {
    removeCondition.removeBlock(cell, grid, "Down");
    grid.updateCells();
    assertEquals(2, grid.getCellFromCoordinate(new Coordinate(1, 0)).getCurrentState(),
        "The cell was not removed");
  }

  @Test
  void incrementBlockInRightDirectionAgent() {
    removeCondition.removeBlock(agent, grid, "Right");
    grid.updateCells();
    assertEquals(2, grid.getCellFromCoordinate(new Coordinate(1, 1)).getCurrentState(),
        "The cell was not removed");
  }

  @Test
  void incrementBlockInvalidDirectionAgent() {
    Boolean result = removeCondition.removeBlock(agent, grid, "None");
    assertEquals(false, result);
  }

  @Test
  void removeNoChangeExpected() {
    Boolean result = removeCondition.removeBlock(cell, grid, "Up");
    assertEquals(false, result);
  }

}