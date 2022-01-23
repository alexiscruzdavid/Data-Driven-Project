package ooga.gamelogic.grid;

import java.util.Objects;

/**
 * Class to represent a coordinate for the cells.
 */
public class Coordinate {

  private int row;
  private int col;

  /**
   * Constructor that sets the x and y of the coordinate.
   *
   * @param row x value
   * @param col y value
   */
  public Coordinate(int row, int col) {
    setNewCoordinate(row, col);
  }

  /**
   * Returns the row of the coordinate.
   *
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column of the coordinate.
   *
   * @return the column
   */
  public int getCol() {
    return col;
  }


  private void setNewCoordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Overrides the equals method for coordinate functionality.
   *
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Coordinate)) {
      return false;
    }
    Coordinate c = (Coordinate) o;
    return (this == o) || (this.getRow() == c.getRow() && this.getCol() == c.getCol());
  }

  /**
   * Overrides the hashcode method for coordinate functionality.
   *
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getRow(), this.getCol());
  }


  @Override
  public String toString() {
    return String.format("X: %d, Y: %d", row, col);
  }
}
