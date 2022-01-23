package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;

/**
 * Extends the Move superclass, moves the given entity down by one cell.
 */
public class DownOneCell extends Move {

  private static final String DOWN_DIRECTION  = "Down";

  /**
   * Constructor for down one cell object, which moves the entity down one space if possible.
   *
   * @param boundaries the boundary checks we want to apply
   */
  public DownOneCell(List<Boundary> boundaries) {
    super(boundaries);
    setDirection(DOWN_DIRECTION);
  }


  /**
   * Returns the correct new coordinate for moving the cell down by one space.
   *
   * @param c the cell we would like to move down
   * @return the new coordinate that the cell will be located at
   */
  @Override
  public Coordinate getNewCoordinate(Cell c) {
    return new Coordinate(c.getCoordinates().getRow() + 1, c.getCoordinates().getCol());
  }
}
