package ooga.gamelogic.grid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class to represent grid data structure that holds cells in the game.
 *
 * @author Alexis
 * @author Kyle White
 */
public class Grid implements Iterable<Integer>, ImmutableGrid {

  private static final String NO_MORE_ELEMENTS_IN_GRID = "No more elements in grid";
  private static final int EMPTY = 0;
  private Map<Coordinate, Cell> cells;
  private int numberOfColumns;
  private int numberOfRows;

  /**
   * Creates grid based on 2d array.
   *
   * @param cells array of states
   */
  public Grid(int[][] cells) {
    initializeGrid(cells);
  }

  /**
   * Creates grid of all empty cells based on the number of rows and columns passed.
   *
   * @param numberOfRows    the number of rows in the grid
   * @param numberOfColumns the number of columns in the grid
   */
  public Grid(int numberOfRows, int numberOfColumns) {
    initializeGrid(numberOfRows, numberOfColumns, EMPTY);
  }

  //Initializes the grid using a 2D array of states.
  private void initializeGrid(int[][] states) {
    this.cells = new HashMap<>();
    this.numberOfColumns = states.length;
    this.numberOfRows = states[0].length;
    for (int r = 0; r < numberOfColumns; r++) {
      for (int c = 0; c < numberOfRows; c++) {
        addCellIfAbsent(r, c, states[r][c]);
      }
    }
  }

  //Initializes the grid using a width, height, and initial state
  private void initializeGrid(int width, int height, int defaultState) {
    this.cells = new HashMap<>();
    this.numberOfColumns = height;
    this.numberOfRows = width;
    for (int r = 0; r < width; r++) {
      for (int c = 0; c < height; c++) {
        addCellIfAbsent(r, c, defaultState);
      }
    }
  }

  /**
   * Adds cell to the grid if it is not currently present in the grid.
   *
   * @param r     row number
   * @param c     col number
   * @param state desired stated
   */
  private void addCellIfAbsent(int r, int c, int state) {
    Coordinate newCell = new Coordinate(r, c);
    for (Coordinate cell : cells.keySet()) {
      if (cell == newCell) {
        return;
      }
    }
    cells.putIfAbsent(new Coordinate(r, c), new Cell(r, c, state));
  }

  /**
   * Adds the passed cell to the grid.
   *
   * @param c the cell to add to the grid
   */
  public void addCell(Cell c) {
    cells.put(c.getCoordinates(), c);
  }

  /**
   * Adds an entity to the grid if space is available.
   *
   * @param entity the entity to add to the grid
   */
  public void addEntity(Entity entity) {
    for (Cell cell : entity.getBody()) {
      Cell currentCell = getCellFromCoordinate(cell.getCoordinates());
      if (currentCell != null && currentCell.getCurrentState() == EMPTY) {
        addCell(cell);
      }
    }
  }


  /**
   * Moves a cell to a new coordinate.
   *
   * @param currentCoordinate the current coordinate of the cell to move
   * @param newCoordinate     the coordinate to which to move the cell to
   */
  public boolean moveCellInGrid(Coordinate currentCoordinate, Coordinate newCoordinate) {
    if (cells.keySet().contains(newCoordinate)) {
      getCellFromCoordinate(newCoordinate).setNextState(
          getCellFromCoordinate(currentCoordinate).getCurrentState());
      return true;
    }
    return false;
  }

  /**
   * Swaps the position of two cells in the grid.
   *
   * @param firstLocation  the current coordinate of the cell to move
   * @param secondLocation the coordinate to which to move the cell to
   */
  public void swapCellInGrid(Coordinate firstLocation, Coordinate secondLocation) {
    Cell currentCell = getCellFromCoordinate(firstLocation);
    Cell cellToSwapWith = getCellFromCoordinate(secondLocation);
    if (cellToSwapWith != null) {
      currentCell.setNextState(cellToSwapWith.getCurrentState());
      getCellFromCoordinate(secondLocation).setNextState(currentCell.getCurrentState());
    }
  }

  /**
   * Updates each cell in the grid. This sets their current states equal to their next states, and
   * sets their next states equal to their current state.
   */
  public void updateCells() {
    for (Coordinate coord : cells.keySet()) {
      updateCell(coord);
    }
  }

  /**
   * Updates an individual cell at the specified coordinates.
   *
   * @param c the coordinate of the cell to update in the grid
   */
  private void updateCell(Coordinate c) {
    Cell cell = cells.get(c);
    cell.setCurrentState(cell.getNextState());
    cell.setNextState(cell.getCurrentState());
  }

  /**
   * Gets the cell at the specified coordinate.
   *
   * @param c the coordinate of the cell to get
   * @return the cell at the given coordinate
   */
  public Cell getCellFromCoordinate(Coordinate c) {
    return cells.get(c);
  }

  public Cell getCell(int row, int col) {
    return getCellFromCoordinate(new Coordinate(row, col));
  }


  /**
   * Gets the list of all cells in the grid.
   *
   * @return all the cells in the grid
   */
  public List<Cell> getCellsInGrid() {
    return new LinkedList<>(cells.values());

  }

  /**
   * Gets an entire row of cells.
   *
   * @param row the row from which to retrieve the cells from
   * @return a list of all the cells in the row
   */
  public List<Cell> getRowOfCells(int row) {
    List<Cell> cellsInRow = new LinkedList<>();
    for (int col = 0; col < numberOfColumns; col++) {
      cellsInRow.add(getCell(row, col));
    }
    return cellsInRow;
  }

  /**
   * Changes the next state of a cell at a particular coordinate to the given next state.
   *
   * @param c     coordinates of cell
   * @param state desired state
   */
  public void changeCell(Coordinate c, int state) {
    cells.get(c).setNextState(state);
  }

  /**
   * The number of columns in the grid.
   *
   * @return width of grid
   */
  public int getNumberOfColumns() {
    return numberOfColumns;
  }

  /**
   * The number of rows in the grid.
   *
   * @return height of grid
   */
  public int getNumberOfRows() {
    return numberOfRows;
  }

  /**
   * Checks if a cell at a given coordinate has a nonzero state.
   *
   * @param coordinate the coordinate at which to check the cell
   * @return whether the cell is full
   */
  public boolean isCellFull(Coordinate coordinate) {
    if (getCellFromCoordinate(coordinate) != null) {
      return getCellFromCoordinate(coordinate).getCurrentState() != EMPTY;
    }
    return false;
  }

  /**
   * Implements the iterator interface for our grid object.
   *
   * @see Iterable#iterator()
   */
  @Override
  public Iterator<Integer> iterator() {
    return new MatrixIterator();
  }

  // Iterator class for matrix
  private class MatrixIterator implements Iterator<Integer> {

    private int row;
    private int col;

    /**
     * Constructor class sets row and col to zero.
     */
    public MatrixIterator() {
      row = 0;
      col = 0;
    }

    @Override
    public boolean hasNext() {
      return !(col > numberOfColumns - 1 || row > numberOfRows - 1);
    }

    @Override
    public Integer next() {
      // assumes there is at least one more element
      if (!hasNext()) {
        throw new NoSuchElementException(NO_MORE_ELEMENTS_IN_GRID);
      }
      int ret = getCell(row, col).getCurrentState();
      if (col < numberOfColumns - 1) {
        col++;
      } else {
        row++;
        col = 0;
      }
      return ret;
    }
  }

  /**
   * Overrides toString to implement the correct functionality for printing displaying the grid as a
   * string.
   *
   * @return the grid as a string
   */
  @Override
  public String toString() {
    String result = "";
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        result = result.concat(getCell(i, j).getCurrentState() + ", ");
      }
      result = result.concat("\n");
    }
    return result;
  }

}
