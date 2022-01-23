package ooga.gamelogic.endgame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.gamelogic.timekeeper.TimeKeeper;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.Test;

/**
 * Class to test the functionality of the Overflow end game class
 *
 * @author Kyle White
 */
class OverflowTest {

  @Test
  void checkConditions() {
    TimeKeeper timer = new TimeKeeper(100, 1);
    int[][] cells = fillGrid(10, 10, 0);
    Grid grid = new Grid(cells);
    Overflow endgame = new Overflow();
    assertFalse(endgame.checkConditions(new Agent(), grid, timer));
    cells[1][0] = 1;
    grid = new Grid(cells);
    endgame = new Overflow();
    assertFalse(endgame.checkConditions(new Agent(), grid, timer));
    cells[0][0] = 1;
    grid = new Grid(cells);
    endgame = new Overflow();
    assertTrue(endgame.checkConditions(new Agent(), grid, timer));
  }

  //Fills grid with specific state
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