package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;


/**
 * Extends the Move class to implement the functionality to move the entity right one cell.
 */
public class RightOneCell extends Move {

  private static final String RIGHT_DIRECTION  = "Right";

  /**
   * Constructor to create the RightOneCell object that moves the given entity right by one cell.
   *
   * @param boundaries the boundaries that we need to check when making the move
   */
  public RightOneCell(List<Boundary> boundaries) {
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
