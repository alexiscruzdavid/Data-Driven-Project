package ooga.gamelogic.endgame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ooga.gamelogic.timekeeper.TimeKeeper;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.Test;

/**
 * Class to test the functionality of the TimeOut end game class
 */
class TimeOutTest {

  @Test
  void checkConditions() {
    int[][] cells = fillGrid(10, 10, 0);
    Grid grid = new Grid(cells);
    TimeKeeper timer = new TimeKeeper(100, -50);
    TimeOut endgame = new TimeOut();
    assertFalse(endgame.checkConditions(new Agent(), grid, timer));
    timer.update();
    assertFalse(endgame.checkConditions(new Agent(), grid, timer));
    timer.update();
    assertTrue(endgame.checkConditions(new Agent(), grid, timer));
    timer.update();
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