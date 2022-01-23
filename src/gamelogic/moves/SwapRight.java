package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Coordinate;

/**
 * Extends Swap class to implement the functionality to swap the current cell with the one to the
 * right of it.
 */
public class SwapRight extends Swap {

  private static final int FIRST_CELL_IN_BODY = 0;

  /**
   * Constructor for Swap, just implements the same functionality as the move constructor.
   *
   * @param boundaries boundaries for the swap
   */
  public SwapRight(List<Boundary> boundaries) {
    super(boundaries);
  }

  /**
   * Returns the correct coordinates for the spot we want to swap the cell into to the right of it.
   *
   * @param currentAgent the current agent we want to swap
   * @return the new coordinate that the cell will be located at after the swap
   */
  @Override
  public Coordinate getLocationToSwapWith(Agent currentAgent) {
    return currentAgent.getDirectRightToBody().get(FIRST_CELL_IN_BODY);

  }

}
