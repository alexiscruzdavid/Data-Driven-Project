package ooga.gamelogic.scorekeeper.scorecalculation;

import java.util.List;

/**
 * This interface defines the method used to calculate the amount to update the score by for a
 * particular game. Each class that implements this interface implements this method in a different
 * way, thus providing different strategies for which to calculate scores in a game.
 */
public interface ScoreCalculation {

  /**
   * Method used to calculate how much to increment the score by based on the states of the cells
   * that have recently been destroyed.
   *
   * @param cellsBeingDestroyed the cells that have recently been destroyed in the game
   * @return an int indicating the amount to add to the current score
   */
  int calculateUpdateAmount(List<Integer> cellsBeingDestroyed);
}
