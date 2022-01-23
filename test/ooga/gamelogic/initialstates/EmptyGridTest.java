package ooga.gamelogic.initialstates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import ooga.controller.DataController;
import ooga.data.GameData;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmptyGridTest {

  private GameData testData;

  @BeforeEach
  public void setUp() throws FileNotFoundException {
    DataController myDataController = new DataController();
    testData = myDataController.getGameConfigurationData(new File("data/config/2048.sim"));
  }

  @Test
  void generateInitialState2by2() {
    int expected00 = 0;
    int expected01 = 0;
    int expected10 = 0;
    int expected11 = 0;
    EmptyGrid initialStateParser = new EmptyGrid();
    Grid startingGrid = initialStateParser.generateInitialState(2, 2, testData);
    assertEquals(expected00, startingGrid.getCell(0, 0).getCurrentState());
    assertEquals(expected01, startingGrid.getCell(0, 1).getCurrentState());
    assertEquals(expected10, startingGrid.getCell(1, 0).getCurrentState());
    assertEquals(expected11, startingGrid.getCell(1, 1).getCurrentState());
  }

  @Test
  void checkGridWidthHeight() {
    int expectedWidth = 20;
    int expectedHeight = 20;
    EmptyGrid initialStateParser = new EmptyGrid();
    Grid startingGrid = initialStateParser.generateInitialState(20, 20, testData);
    assertEquals(expectedHeight, startingGrid.getNumberOfColumns());
    assertEquals(expectedWidth, startingGrid.getNumberOfRows());
  }

}