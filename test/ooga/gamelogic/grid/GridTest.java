package ooga.gamelogic.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GridTest {

  private Grid grid;

  @BeforeEach
  public void setUp() {
    grid = new Grid(10, 10);
    grid.changeCell(new Coordinate(1, 2), 25);
    grid.changeCell(new Coordinate(0, 3), 3);
    grid.changeCell(new Coordinate(0, 2), 3);
    grid.updateCells();
  }

  @Test
  void initializeGridHeightWidthTest() {
    int expectedHeight = 3;
    int expectedWidth = 3;
    Grid testGrid = new Grid(3, 3);
    assertEquals(expectedHeight, testGrid.getNumberOfColumns());
    assertEquals(expectedWidth, testGrid.getNumberOfRows());
  }

  @Test
  void initializeInitialStatesZeroTest() {
    int expectedState = 0;
    Grid testGrid = new Grid(3, 3);
    assertEquals(expectedState, testGrid.getCell(2, 2).getCurrentState());
  }


  @Test
  void initializeInitialStatesFromPresetArrayTest() {
    int[][] cells = fillGrid(10, 10, 10);
    int expectedState = 10;
    Grid testGrid = new Grid(cells);
    assertEquals(expectedState, testGrid.getCell(2, 2).getCurrentState());
  }

  @Test
  void getCellAtNonexistentCoordinate() {
    Grid testGrid = new Grid(3, 3);
    assertEquals(null, testGrid.getCell(5, 5));
  }

  @Test
  void moveCellInGrid() {
    int expectedState = 25;
    grid.moveCellInGrid(new Coordinate(1, 2), new Coordinate(2, 2));
    grid.updateCells();
    assertEquals(grid.getCell(2, 2).getCurrentState(), expectedState);
  }

  @Test
  void moveCellInGridFarAway() {
    int expectedState = 25;
    grid.moveCellInGrid(new Coordinate(1, 2), new Coordinate(5, 2));
    grid.updateCells();
    assertEquals(grid.getCell(5, 2).getCurrentState(), expectedState);
  }

  @Test
  void moveToInvalidSpot() {
    grid.moveCellInGrid(new Coordinate(1, 2), new Coordinate(500, 2));
    assertThrows(NullPointerException.class,
        () -> grid.getCellFromCoordinate(new Coordinate(500, 2)).getCurrentState());
  }

  @Test
  void swapFullCellsInGrid() {
    int expectedState1 = 3;
    int expectedState2 = 25;
    grid.swapCellInGrid(new Coordinate(1, 2), new Coordinate(0, 3));
    grid.updateCells();
    assertEquals(grid.getCell(1, 2).getCurrentState(), expectedState1);
    assertEquals(grid.getCell(0, 3).getCurrentState(), expectedState2);
  }

  @Test
  void swapCellInGridWithEmptyCell() {
    int expectedState1 = 3;
    int expectedState2 = 0;
    grid.swapCellInGrid(new Coordinate(0, 0), new Coordinate(0, 3));
    grid.updateCells();
    assertEquals(grid.getCell(0, 0).getCurrentState(), expectedState1);
    assertEquals(grid.getCell(0, 3).getCurrentState(), expectedState2);
  }

  @Test
  void swapCellInvalidSwap() {
    grid.swapCellInGrid(new Coordinate(0, 0), new Coordinate(0, 3));
    grid.updateCells();
    grid.swapCellInGrid(new Coordinate(1, 2), new Coordinate(500, 2));
    assertEquals(25, grid.getCell(1, 2).getCurrentState());
  }

  @Test
  void gridIteratorTest() {
    int[][] cells = fillGrid(10, 10, 0);
    Grid grid = new Grid(cells);
    Iterator<Integer> it = grid.iterator();
    for (int i = 0; i < 100; i++) {
      assertTrue(it.hasNext());
      assertEquals(0, it.next());
    }
    assertFalse(it.hasNext());
    Exception exception = assertThrows(NoSuchElementException.class,
        () -> it.next());
    String actualMessage = exception.getMessage();
    String expected = "No more elements in grid";
    assertEquals(actualMessage, expected);

  }

  private int[][] fillGrid(int rows, int cols, int state) {
    int[][] ret = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        ret[i][j] = state;
      }
    }
    return ret;
  }

}