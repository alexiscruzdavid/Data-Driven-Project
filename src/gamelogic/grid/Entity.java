package ooga.gamelogic.grid;


import java.util.List;

/**
 * A interface representing an entity, which is any object that acts as an individual within the
 * grid. This can be implemented as a group of cells or a single cell on the board. Each entity has
 * a body and can obtain the neighbors surrounding it.
 */
public interface Entity {

  /**
   * Gets the body of the entity, which is a list of cells makes it up.
   *
   * @return the list of cells that makes up the entity
   */
  List<Cell> getBody();

  /**
   * Gets all the coordinates of the cells to the left of the entity.
   *
   * @return list of the coordinates to the left of the entity
   */
  List<Coordinate> getDirectLeftToBody();

  /**
   * Gets all the coordinates of the cells below the entity.
   *
   * @return list of the coordinates below the entity
   */
  List<Coordinate> getDirectDownToBody();


  /**
   * Gets all the coordinates of the cells to the right of the entity.
   *
   * @return list of the coordinates to the right of the entity
   */

  List<Coordinate> getDirectRightToBody();


  /**
   * Gets all the coordinates of the cells above the entity.
   *
   * @return list of the coordinates above the entity
   */
  List<Coordinate> getDirectUpToBody();
}
