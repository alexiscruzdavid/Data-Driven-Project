package ooga.gamelogic.moves;

import java.util.List;
import ooga.gamelogic.boundary.Boundary;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Coordinate;


/**
 * Extends the Move superclass, moves the given entity down until it hits a boundary.
 */
public class DownUntilBoundary extends MoveUntilBoundary {

  private static final String DOWN_DIRECTION  = "Down";

  /**
   * Constructor for the DownUntilBoundary object, which moves the object down until a boundary.
   *
   * @param boundaries the boundary checks we want to apply to the move
   */
  public DownUntilBoundary(List<Boundary> boundaries) {
    super(boundaries);
    setDirection(DOWN_DIRECTION);
  }

  /**
   * Method to get the new coordinate to move the cell down on spot.
   *
   * @param c the cell we want to move
   * @return the new coordinate for the location of the cell
   */
  @Override
  public Coordinate getNewCoordinate(Cell c) {
    return new Coordinate(c.getCoordinates().getRow() + 1, c.getCoordinates().getCol());
  }
}
