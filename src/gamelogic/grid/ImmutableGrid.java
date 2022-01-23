package ooga.gamelogic.grid;

import java.util.Iterator;

/**
 * An interface designed to encapsulate our grid object. It provides only the methods necessary for
 * other parts of the program to obtain information about the grid.
 */
public interface ImmutableGrid {

  /**
   * Provides the iterator for our grid, which is used to obtain information about all the states of
   * the cells.
   *
   * @return an iterator object that can be used to iterate over the grid and obtain cell state
   *          information
   */
  Iterator<Integer> iterator();

  /**
   * Gets the number of rows in the grid.
   *
   * @return the number of rows
   */
  int getNumberOfRows();

  /**
   * Gets the number of columns in the grid.
   *
   * @return the number of columns
   */
  int getNumberOfColumns();

}
