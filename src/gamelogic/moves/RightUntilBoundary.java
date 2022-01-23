package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;


/**
 * Extends the Move superclass, Moves the given entity right until it hits a boundary.
 */
public class RightUntilBoundary extends MoveUntilBoundary {

  private static final String RIGHT_DIRECTION  = "Right";

  /**
   * Constructor for the RightUntilBoundary object, which moves the object right until a boundary.
   *
   * @param boundaries the boundary checks we want to apply to the move
   */
  public RightUntilBoundary(List<Boundary> boundaries) {
    super(boundaries);
    setDirection(RIGHT_DIRECTION);
  }

  /**
   * Returns the correct new coordinate for moving the cell right by one space.
   *
   * @param c the cell we would like to move right
   * @return the new coordinate that the cell will be located at
   */
  @Override
  public Coordinate getNewCoordinate(Cell c) {
    return new Coordinate(c.getCoordinates().getRow(), c.getCoordinates().getCol() + 1);
  }
}
