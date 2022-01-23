package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;

/**
 * Extends the Move class to implement the functionality to move the entity up one cell.
 */

public class UpOneCell extends Move {
  private static final String UP_DIRECTION  = "Up";

  /**
   * Constructor to create the UpOneCell object that moves the given entity up by one cell.
   *
   * @param boundaries the boundaries that we need to check when making the move
   */
  public UpOneCell(List<Boundary> boundaries) {
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
