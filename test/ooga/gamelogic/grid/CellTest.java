package ooga.gamelogic.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class CellTest {

  @Test
  void equalsTest() {
    Cell testCell = new Cell(0, 0, 0);
    String notCell = "Not a cell";
    assertFalse(testCell.equals(notCell));
    Cell differentCoordinateCell = new Cell(0, 1, 0);
    assertFalse(testCell.equals(differentCoordinateCell));
    Cell differentStateCell = new Cell(0, 0, 1);
    assertFalse(testCell.equals(differentStateCell));
    Cell sameCell = new Cell(0, 0, 0);
    assertTrue(testCell.equals(sameCell));
    assertTrue(testCell.equals(testCell));
  }

  @Test
  void cellStatesAndCoordinates() {
    Cell testCell = new Cell(0, 0, 5);
    assertEquals(5, testCell.getCurrentState());
    assertEquals(new Coordinate(0, 0), testCell.getCoordinates());
    testCell.setNextState(4);
    assertEquals(4, testCell.getNextState());
    testCell.setCurrentState(17);
    assertEquals(17, testCell.getCurrentState());
    testCell.setCoordinate(new Coordinate(5, 5));
    assertEquals(new Coordinate(5, 5), testCell.getCoordinates());
  }

  @Test
  void getCellsInEachDirection() {
    Cell testCell = new Cell(1, 1, 1);
    assertEquals(new Coordinate(0, 1), testCell.getDirectUpToBody().get(0));
    assertEquals(new Coordinate(1, 2), testCell.getDirectRightToBody().get(0));
    assertEquals(new Coordinate(1, 0), testCell.getDirectLeftToBody().get(0));
    assertEquals(new Coordinate(2, 1), testCell.getDirectDownToBody().get(0));
  }


  @Test
  void getCellsOutOfBounds() {
    Cell testCell = new Cell(0, 0, 1);
    assertEquals(new Coordinate(-1, 0), testCell.getDirectUpToBody().get(0));
  }

  @Test
  void cellBody() {
    Cell testCell = new Cell(0, 0, 1);
    assertEquals(List.of(testCell), testCell.getBody());
  }

}