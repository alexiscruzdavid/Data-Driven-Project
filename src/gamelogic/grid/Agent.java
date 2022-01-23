package ooga.gamelogic.grid;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import ooga.exceptions.ReflectionException;

/**
 * Class to represent a group of cells in the grid.
 */
public class Agent implements Entity {

  private static final String INVALID_CELL_DIRECTION = "Invalid direction passed to get cells from";
  private static final String METHOD_TO_GET_NEXT_TO_BODY = "get%sToBody";
  private static final String LEFT = "DirectLeft";
  private static final String DOWN = "DirectDown";
  private static final String RIGHT = "DirectRight";
  private static final String UP = "DirectUp";
  private static final int DIMENSIONS_OF_AGENT = 2;

  private List<Cell> body;
  private Random myRandom = new Random();

  /**
   * Constructor that initializes body.
   */
  public Agent() {
    body = new LinkedList<>();
  }

  /**
   * Constructor that initializes body with numCells set to a specific state with starting (x,y)
   * position for the first cell the cells will appear in a row.
   */
  public Agent(int row, int col, int numCells, int state) {
    body = new LinkedList<>();
    for (int i = 0; i < numCells; i++) {
      body.add(new Cell(row, col + i, state));
    }
  }

  /**
   * Constructor that takes in the body.
   *
   * @param body list of cells
   */
  public Agent(List<Cell> body) {
    this.body = body;
  }

  /**
   * Gets the body of the agent.
   *
   * @return body of cells
   */
  public List<Cell> getBody() {
    return body;
  }

  private List<Coordinate> removeRepeatedEntries(List<Coordinate> list) {
    List<Coordinate> coordsToRemove = new ArrayList<>();
    for (Coordinate coordinate : list) {
      if (containsCoordinate(coordinate)) {
        coordsToRemove.add(coordinate);
      }
    }
    list.removeAll(coordsToRemove);
    return list;
  }

  /**
   * updates the body with a set of new cells.
   *
   * @param newCells the new cells with which to populate the body
   */
  public void update(List<Cell> newCells) {
    body = new LinkedList<>();
    for (Cell c : newCells) {
      addCell(c);
    }
  }

  private boolean containsCoordinate(Coordinate coordinate) {
    for (Cell c : getBody()) {
      if (c.getCoordinates().equals(coordinate)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieves the adjacent coordinates (in a given direction) for a body.
   *
   * @param direction the direction we want to retrieve the coordinates from
   * @return the list of coordinates in the given direction
   */
  private List<Coordinate> retrieveAdjacentCells(String direction) {
    List<Coordinate> neighborCoordinates = new LinkedList<>();
    body.forEach(cell -> {
      try {
        neighborCoordinates.addAll(
            (List<Coordinate>) cell.getClass()
                .getMethod(String.format(METHOD_TO_GET_NEXT_TO_BODY, direction))
                .invoke(cell));
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new ReflectionException(INVALID_CELL_DIRECTION);
      }
    });
    return removeRepeatedEntries(neighborCoordinates);
  }

  /**
   * Gets the coordinates of all cells directly to the left of the body.
   *
   * @return coordinates of cells directly to the left of the body
   */
  @Override
  public List<Coordinate> getDirectLeftToBody() {
    return retrieveAdjacentCells(LEFT);
  }

  /**
   * Gets the coordinates of all cells directly below the body.
   *
   * @return coordinates of cells directly below the body
   */
  @Override
  public List<Coordinate> getDirectDownToBody() {
    return retrieveAdjacentCells(DOWN);
  }

  /**
   * Gets the coordinates of all cells directly right of the body.
   *
   * @return coordinates of cells directly right of the body
   */
  @Override
  public List<Coordinate> getDirectRightToBody() {
    return retrieveAdjacentCells(RIGHT);
  }

  /**
   * Gets the coordinates of all cells directly above the body.
   *
   * @return coordinates of cells directly above the body
   */
  @Override
  public List<Coordinate> getDirectUpToBody() {
    return retrieveAdjacentCells(UP);
  }

  /**
   * Adds a given cell to the current body.
   *
   * @param c cell to be added to the body
   */
  public void addCell(Cell c) {
    for (Cell cell : body) {
      if (cell != null && cell.equals(c)) {
        return;
      }
    }
    body.add(c);
  }

  /**
   * Generates a random body in an organized "shape" for a given agent.
   *
   * @param startRow the row of the first block in the body
   * @param startCol the column of the first block in the body
   * @param state    the state for all cells in the body
   * @param numCells the number of cells in the body
   */
  public void generateRandomBody(int startRow, int startCol, int state, int numCells) {
    body.clear();
    int newRandom1 = myRandom.nextInt(DIMENSIONS_OF_AGENT);
    int newRandom2 = 1 - newRandom1;
    for (int i = 0; i < numCells; i++) {
      body.add(new Cell((startRow + newRandom1), (startCol + newRandom2), state));
      startRow = (startRow + newRandom1);
      startCol = (startCol + newRandom2);
      newRandom1 = myRandom.nextInt(DIMENSIONS_OF_AGENT);
      newRandom2 = 1 - newRandom1;
    }
  }

  protected void setRandomSeed(int seed) {
    myRandom.setSeed(seed);
  }
}
