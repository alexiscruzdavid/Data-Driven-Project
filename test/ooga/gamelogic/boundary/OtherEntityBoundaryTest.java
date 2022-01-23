package ooga.gamelogic.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OtherEntityBoundaryTest {

  int gridWidth = 10;
  int gridHeight = 10;
  OtherEntityBoundary boundary;
  Entity agent;
  Entity cell;
  Grid grid;

  @BeforeEach
  void setup() {
    boundary = new OtherEntityBoundary();
    agent = new Agent(gridWidth - 2, gridHeight - 2, 2, 1);
    cell = new Cell(gridWidth - 1, gridHeight - 1, 1);
    grid = new Grid(gridWidth, gridHeight);
    grid.addEntity(cell);
    grid.addEntity(agent);
  }


  @Test
  void detectsCellColliding() {
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Up"),
        "The block intersect but the algorithm says otherwise");
  }

  @Test
  void boundaryInvalidDirection() {
    cell = new Cell(0, 5, 1);
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Cool"),
        "The block intersect with wall but the algorithm says otherwise");
  }

  @Test
  void detectsCellAdjacentToEntity() {
    cell = new Cell(gridWidth - 1, gridHeight - 2, 1);
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Right"),
        "The block intersect but the algorithm says otherwise");
  }

  @Test
  void cellIsNotTouchingOtherEntity() {
    cell = new Cell(0, 0, 1);
    assertEquals(false, boundary.isHittingBoundary(cell, grid, "Down"),
        "The block don't intersect but the algorithm says otherwise");
  }

  @Test
  void detectsAgentColliding() {
    assertEquals(true, boundary.isHittingBoundary(agent, grid, "Down"),
        "The block intersect but the algorithm says otherwise");
  }

  @Test
  void detectsAgentAdjacentToEntity() {
    cell = new Cell(gridWidth - 1, gridHeight - 2, 1);
    assertEquals(true, boundary.isHittingBoundary(agent, grid, "Down"),
        "The block intersect but the algorithm says otherwise");
  }

  @Test
  void agentIsNotTouchingOtherEntity() {
    cell = new Cell(0, 0, 1);
    for (Coordinate c : agent.getDirectDownToBody()) {
      if (grid.getCellFromCoordinate(c) != null) {
        grid.getCellFromCoordinate(c).setCurrentState(2);
      }
    }
    assertEquals(true, boundary.isHittingBoundary(agent, grid, "Down"),
        "The block don't intersect but the algorithm says otherwise");
  }

}