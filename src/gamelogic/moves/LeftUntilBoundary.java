package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;


/**
 * Extends the Move superclass, Moves the given entity left until it hits a boundary.
 */
public class LeftUntilBoundary extends MoveUntilBoundary {


  private static final String LEFT_DIRECTION  = "Left";
  /**
   * Constructor for the LeftUntilBoundary object, which moves the object left until a boundary.
   *
   * @param boundaries the boundary checks we want to apply to the move
   */
  public LeftUntilBoundary(List<Boundary> boundaries) {
    super(boundaries);
    setDirection(LEFT_DIRECTION);
  }

  /**
   * Returns the correct new coordinate for moving the cell left by one space.
   *
   * @param c the cell we would like to move down
   * @return the new coordinate that the cell will be located at
   */
  @Override
  public Coordinate getNewCoordinate(Cell c) {
    return new Coordinate(c.getCoordinates().getRow(), c.getCoordinates().getCol() - 1);
  }
}
