package ooga.gamelogic.grid;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class to represent a cell in a grid. Each cell has a coordinate and has its current state and
 * next state stored.
 */
public class Cell implements Entity {

  private Coordinate coordinate;
  private int currentState;
  private int nextState;

  /**
   * Constructs a new Cell at the specified coordinates with the given state and alternate state.
   *
   * @param x     the row of the cell
   * @param y     the column of the cell
   * @param state the current state of the cell
   */
  public Cell(int x, int y, int state) {
    this.coordinate = new Coordinate(x, y);
    this.currentState = state;
    this.nextState = state;
  }

  /**
   * Returns the coordinate of the cell.
   *
   * @return coordinate of cell
   */
  public Coordinate getCoordinates() {
    return coordinate;
  }

  /**
   * Returns the current state of the cell.
   *
   * @return the current state of the cell
   */
  public int getCurrentState() {
    return currentState;
  }

  /**
   * Sets the value of this cell's state to the specified state.
   *
   * @param state the new state of this cell
   */
  public void setCurrentState(int state) {
    currentState = state;
  }

  /**
   * Returns the next state of the cell.
   *
   * @return the next state of the cell
   */
  public int getNextState() {
    return nextState;
  }

  /**
   * Sets the value of this cell's next state to the specified state.
   *
   * @param state the next state of this cell
   */
  public void setNextState(int state) {
    nextState = state;
  }

  public void setCoordinate(Coordinate newCoordinate) {
    coordinate = newCoordinate;
  }


  /**
   * Overrides the equals method to check if two cells are equal based on current state and
   * coordinates.
   *
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Cell)) {
      return false;
    }
    Cell c = (Cell) o;
    return this == o || (this.currentState == c.currentState
        && this.getCoordinates().equals(c.getCoordinates()));
  }

  /**
   * Overrries the hashcode method to use the coordinates and current state to determine the
   * hashcode.
   *
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.getCoordinates(), this.currentState);
  }

  /**
   * The body of the cell, which is just itself.
   *
   * @return a list whose only item is the current cell
   */
  @Override
  public List<Cell> getBody() {
    return new ArrayList<>(List.of(this));
  }

  /**
   * Gets coordinate for cell directly left of the current cell.
   *
   * @return coordinate directly left of cell
   */
  @Override
  public List<Coordinate> getDirectLeftToBody() {
    List<Coordinate> neighborCoordinates = new LinkedList<>();
    neighborCoordinates.add(
        new Coordinate(this.getCoordinates().getRow(), this.getCoordinates().getCol() - 1));
    return neighborCoordinates;
  }

  /**
   * Gets coordinate for cell directly below the current cell.
   *
   * @return coordinate directly below the cell
   */
  @Override
  public List<Coordinate> getDirectDownToBody() {
    List<Coordinate> neighborCoordinates = new LinkedList<>();
    neighborCoordinates.add(
        new Coordinate(this.getCoordinates().getRow() + 1, this.getCoordinates().getCol()));
    return neighborCoordinates;
  }

  /**
   * Gets coordinate for cell directly right of the current cell.
   *
   * @return coordinate directly right of cell
   */
  @Override
  public List<Coordinate> getDirectRightToBody() {
    List<Coordinate> neighborCoordinates = new LinkedList<>();
    neighborCoordinates.add(
        new Coordinate(this.getCoordinates().getRow(), this.getCoordinates().getCol() + 1));
    return neighborCoordinates;
  }

  /**
   * Gets coordinate for cell directly above the current cell.
   *
   * @return coordinate directly above cell
   */
  @Override
  public List<Coordinate> getDirectUpToBody() {
    List<Coordinate> neighborCoordinates = new LinkedList<>();
    neighborCoordinates.add(
        new Coordinate(this.getCoordinates().getRow() - 1, this.getCoordinates().getCol()));
    return neighborCoordinates;
  }
}
