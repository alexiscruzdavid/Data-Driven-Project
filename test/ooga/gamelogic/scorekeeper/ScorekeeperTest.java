package ooga.gamelogic.scorekeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.scorekeeper.scorecalculation.ScoreCalculation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScorekeeperTest {

  private Grid grid;
  private Scorekeeper myScorekeeper;
  private ScoreCalculation scoreCalc;

  @BeforeEach
  public void setUp() {
    myScorekeeper = new Scorekeeper(0, "ScaledByState");
    grid = new Grid(10, 10);
    grid.changeCell(new Coordinate(1, 2), 3);
    grid.changeCell(new Coordinate(0, 1), 1);
    grid.changeCell(new Coordinate(4, 2), 3);
    grid.updateCells();

  }

  @Test
  void updateScoreStateValue() {
    int expected = 16;
    List<Integer> myCellsToDestroy = List.of(grid.getCell(1, 2).getCurrentState());
    myScorekeeper.updateScore(myCellsToDestroy);
    assertEquals(expected, myScorekeeper.getCurrentScore());
  }

  @Test
  void updateScoreNoCellsToDestroy() {
    int expected = 0;
    List<Integer> myCellsToDestroy = List.of();
    myScorekeeper.updateScore(myCellsToDestroy);
    assertEquals(expected, myScorekeeper.getCurrentScore());
  }

  @Test
  void updateScoreSeveralRounds() {
    int expected = 52;
    List<Integer> myCellsToDestroy = List.of(grid.getCell(1, 2).getCurrentState(),
        grid.getCell(4, 2).getCurrentState());
    myScorekeeper.updateScore(myCellsToDestroy);
    myCellsToDestroy = List.of(grid.getCell(1, 2).getCurrentState(),
        grid.getCell(0, 1).getCurrentState());
    myScorekeeper.updateScore(myCellsToDestroy);
    assertEquals(expected, myScorekeeper.getCurrentScore());
  }

  @Test
  void getInitialScore() {
    int expected = 0;
    assertEquals(expected, myScorekeeper.getCurrentScore());
  }

  @Test
  void setScoreCalcNumBlocksDestroyed() {
    Boolean expected = true;
    Boolean result = myScorekeeper.setScoreAddingCalculator("NumBlocksDestroyed");
    assertEquals(expected, result);
  }

  @Test
  void setScoreCalcStateValue() {
    Boolean expected = true;
    Boolean result = myScorekeeper.setScoreAddingCalculator("ScaledByState");
    assertEquals(expected, result);
  }

  @Test
  void setScoreCalcInvalid() {
    Boolean expected = false;
    Boolean result = myScorekeeper.setScoreAddingCalculator("INVALID");
    assertEquals(expected, result);
  }

}