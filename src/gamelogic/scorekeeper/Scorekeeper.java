package ooga.gamelogic.scorekeeper;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ooga.gamelogic.scorekeeper.scorecalculation.ScoreCalculation;

/**
 * Class to keep the score of the game. Applies the particular chosen score calculation logic for a
 * game to determine how to update (increase) the score if blocks have been destroyed.
 */
public class Scorekeeper {

  private static final String PATH_TO_SCORE_CALC_LOGIC = "ooga.gamelogic.scorekeeper.scorecalculation.%sCalculator";
  private static final int STARTING_SCORE = 0;
  private int currentScore;
  private ScoreCalculation scoreAddingCalculator;


  /**
   * Constructor for the scorekeeper object. Sets the current score to starting score and sets the
   * score calculating scheme.
   *
   * @param startingScore          the initial score
   * @param scoreCalculationScheme the logic to use in calculating the score
   */
  public Scorekeeper(int startingScore, String scoreCalculationScheme) {
    currentScore = startingScore;
    setScoreAddingCalculator(scoreCalculationScheme);
  }

  /**
   * Method used to update the score based on the states of the cells that have been destroyed in
   * the current step.
   *
   * @param destroyedCells the states of the cells that have recently been destroyed
   */
  public void updateScore(List<Integer> destroyedCells) {
    if (scoreAddingCalculator != null) {
      currentScore += scoreAddingCalculator.calculateUpdateAmount(destroyedCells);
    }
  }

  /**
   * Returns the current score of the game.
   *
   * @return the game's current score
   */
  public int getCurrentScore() {
    return currentScore;
  }

  protected boolean setScoreAddingCalculator(String scoreCalculationScheme) {
    try {
      scoreAddingCalculator = (ScoreCalculation) Class.forName(
              String.format(PATH_TO_SCORE_CALC_LOGIC, scoreCalculationScheme)).getConstructor()
          .newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
        | NoSuchMethodException | ClassNotFoundException e) {
      return false;
    }
    return true;
  }

  /**
   * Resets the game score to zero.
   */
  public void resetScore() {
    currentScore = STARTING_SCORE;
  }
}

