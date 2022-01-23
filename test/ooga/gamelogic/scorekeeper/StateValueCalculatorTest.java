package ooga.gamelogic.scorekeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ooga.gamelogic.grid.Coordinate;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.scorekeeper.scorecalculation.ScaledByStateCalculator;
import ooga.gamelogic.scorekeeper.scorecalculation.ScoreCalculation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateValueCalculatorTest {

  private Grid grid;
  private ScoreCalculation myScoreCalc;

  @BeforeEach
  public void setUp() {
    myScoreCalc = new ScaledByStateCalculator();
    grid = new Grid(10, 10);
    grid.changeCell(new Coordinate(1, 2), 3);
    grid.changeCell(new Coordinate(0, 1), 3);
    grid.changeCell(new Coordinate(4, 2), 7);
    grid.updateCells();
  }

  @Test
  void updateScoreStateValueOneCell() {
    int expected = 16;
    List<Integer> myCellsToDestroy = List.of(grid.getCell(1, 2).getCurrentState());
    int updateAmt = myScoreCalc.calculateUpdateAmount(myCellsToDestroy);
    assertEquals(expected, updateAmt);
  }

  @Test
  void updateScoreStateValueSeveralCells() {
    int expected = 272;
    List<Integer> myCellsToDestroy = List.of(grid.getCell(1, 2).getCurrentState(),
        grid.getCell(4, 2).getCurrentState());
    int updateAmt = myScoreCalc.calculateUpdateAmount(myCellsToDestroy);
    assertEquals(expected, updateAmt);
  }

  @Test
  void updateScoreStateValueNoCells() {
    int expected = 0;
    List<Integer> myCellsToDestroy = List.of();
    int updateAmt = myScoreCalc.calculateUpdateAmount(myCellsToDestroy);
    assertEquals(expected, updateAmt);
  }
}