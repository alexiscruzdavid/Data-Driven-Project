package ooga.gamelogic.scorekeeper.scorecalculation;

import java.util.List;

/**
 * Implements the ScoreCalculation interface to provide the specific implementation for a game that
 * uses two raised to the power of the destroyed cell state times two to determine the addition to
 * the score.
 */
public class ScaledByStateCalculator implements ScoreCalculation {

  private static final int STARTING_VALUE = 0;
  private static final int POWER = 2;
  private static final int NUM_CELLS_DESTROYED = 2;

  /**
   * Calculates the update amount by raising two to the power of the destroyed cell state and
   * multiplying by two.
   *
   * @param cellsBeingDestroyed the cells that have recently been destroyed in the game
   * @return an int containing the amount with which to update the score
   */
  @Override
  public int calculateUpdateAmount(List<Integer> cellsBeingDestroyed) {
    int toAdd = STARTING_VALUE;
    for (Integer currentCellState : cellsBeingDestroyed) {
      toAdd += Math.pow(POWER, currentCellState) * NUM_CELLS_DESTROYED;
    }
    return toAdd;
  }
}
