package ooga.gamelogic.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Entity;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EdgeBoundaryTest {

  int gridWidth = 10;
  int gridHeight = 10;
  EdgeBoundary boundary;
  Entity agent;
  Entity cell;
  Grid grid;

  @BeforeEach
  void setup() {
    boundary = new EdgeBoundary();
    agent = new Agent(gridWidth - 2, gridHeight - 2, 2, 1);
    cell = new Cell(0, 0, 1);
    grid = new Grid(gridWidth, gridHeight);
    grid.addEntity(cell);
    grid.addEntity(agent);
  }


  @Test
  void cellDetectsWallRight() {
    assertEquals(false, boundary.isHittingBoundary(cell, grid, "Right"),
        "The block intersect with wall but the algorithm says otherwise");
  }

  @Test
  void cellDetectsWallLeft() {
    cell = new Cell(0, 0, 1);
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Left"),
        "The block intersect with wall but the algorithm says otherwise");
  }

  @Test
  void cellDetectsWallUp() {
    cell = new Cell(0, 5, 1);
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Up"),
        "The block intersect with wall but the algorithm says otherwise");
  }


  @Test
  void boundaryInvalidDirection() {
    cell = new Cell(0, 5, 1);
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Cool"),
        "The block intersect with wall but the algorithm says otherwise");
  }


  @Test
  void noWallDetectedUp() {
    cell = new Cell(1, 5, 1);
    assertEquals(false, boundary.isHittingBoundary(cell, grid, "Up"),
        "The block intersect with wall but the algorithm says otherwise");
  }

  @Test
  void cellDetectsWallOnOppositeEnd1() {
    cell = new Cell(gridWidth - 1, 0, 1);
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Down"),
        "The block doesn't intersect with wall but the algorithm says otherwise");
  }

  @Test
  void cellDetectsWallOnOppositeEnd2() {
    cell = new Cell(0, gridHeight - 1, 1);
    assertEquals(true, boundary.isHittingBoundary(cell, grid, "Right"),
        "The block doesn't intersect with wall but the algorithm says otherwise");
  }

  @Test
  void cellDetectsWallOnOppositeEnd3() {
    cell = new Cell(0, 0, 1);
    assertEquals(false, boundary.isHittingBoundary(cell, grid, "Right"),
        "The block doesn't intersect with wall but the algorithm says otherwise");
  }

  @Test
  void agentDetectsWall() {
    assertEquals(true, boundary.isHittingBoundary(agent, grid, "Right"),
        "The block intersect with wall but the algorithm says otherwise");
  }


}