package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;


/**
 * Extends the Move superclass, Moves the given entity up until it hits a boundary.
 */
public class UpUntilBoundary extends MoveUntilBoundary {

  private static final String UP_DIRECTION  = "Up";

  /**
   * Constructor for the UpUntilBoundary object, which moves the object up until a boundary.
   *
   * @param boundaries the boundary checks we want to apply to the move
   */
  public UpUntilBoundary(List<Boundary> boundaries) {
    super(boundaries);
    setDirection(UP_DIRECTION);
  }

  /**
   * Returns the correct new coordinate for moving the cell up by one space.
   *
   * @param c the cell we would like to move up
   * @return the new coordinate that the cell will be located at
   */
  @Override
  public Coordinate getNewCoordinate(Cell c) {
    return new Coordinate(c.getCoordinates().getRow() - 1, c.getCoordinates().getCol());
  }
}
