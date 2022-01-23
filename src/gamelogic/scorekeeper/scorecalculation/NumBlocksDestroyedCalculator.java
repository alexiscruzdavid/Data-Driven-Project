package ooga.gamelogic.scorekeeper.scorecalculation;

import java.util.List;


/**
 * Implements the ScoreCalculation interface to provide the specific implementation for a game that
 * uses the number of cells destroyed to calculate the addition to the score.
 */
public class NumBlocksDestroyedCalculator implements ScoreCalculation {

  private static final int STARTING_VALUE = 0;
  private static final int INCREMENT = 1;

  /**
   * Calculates the update amount adding one to the score to add for every cell that has been
   * destroyed.
   *
   * @param cellsBeingDestroyed the cells that have recently been destroyed in the game
   * @return an int containing the amount with which to update the score
   */
  @Override
  public int calculateUpdateAmount(List<Integer> cellsBeingDestroyed) {
    int toAdd = STARTING_VALUE;
    for (Integer currentCellState : cellsBeingDestroyed) {
      toAdd += INCREMENT;
    }
    return toAdd;
  }
}
