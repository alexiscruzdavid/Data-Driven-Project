package ooga.gamelogic.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoordinateTest {

  @Test
  void equalsTest() {
    Coordinate testCoordinate = new Coordinate(0, 0);
    String notCoordinate = "Not a coordinate";
    boolean result = testCoordinate.equals(notCoordinate);
    assertFalse(result);
    Coordinate differentXCoordinate = new Coordinate(1, 0);
    assertFalse(testCoordinate.equals(differentXCoordinate));
    Coordinate differentYCoordinate = new Coordinate(0, 1);
    assertFalse(testCoordinate.equals(differentYCoordinate));
    Coordinate sameCoordinate = new Coordinate(0, 0);
    assertTrue(testCoordinate.equals(sameCoordinate));
    assertTrue(testCoordinate.equals(testCoordinate));
  }

  @Test
  void getRowAndColOfCoordinate() {
    Coordinate testCoordinate = new Coordinate(1, 5);
    assertEquals(5, testCoordinate.getCol());
    assertEquals(1, testCoordinate.getRow());
  }

  @Test
  void toStringOfCoordinate() {
    Coordinate testCoordinate = new Coordinate(1, 5);
    assertEquals("X: 1, Y: 5", testCoordinate.toString());
  }

}