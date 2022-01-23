package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;


/**
 * Extends the Move class to implement the functionality to move the entity left one cell.
 */
public class LeftOneCell extends Move {

  private static final String LEFT_DIRECTION  = "Left";

  /**
   * Constructor to create the LeftOneCell object that moves the given entity left by one cell.
   *
   * @param boundaries the boundaries that we need to check when making the move
   */
  public LeftOneCell(List<Boundary> boundaries) {
    super(boundaries);
    setDirection(LEFT_DIRECTION);
  }


  /**
   * Returns the correct new coordinate for moving the cell left by one space.
   *
   * @param c the cell we would like to move left
   * @return the new coordinate that the cell will be located at
   */
  @Override
  public Coordinate getNewCoordinate(Cell c) {
    if (c != null) {
      return new Coordinate(c.getCoordinates().getRow(), c.getCoordinates().getCol() - 1);
    }
    return null;
  }
}
