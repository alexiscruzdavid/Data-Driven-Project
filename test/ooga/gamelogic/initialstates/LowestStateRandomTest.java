package ooga.gamelogic.initialstates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import ooga.controller.DataController;
import ooga.data.GameData;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LowestStateRandomTest {

  private Map<String, String> gameParams = new HashMap<>();
  LowestStateRandom initialStateParser;
  private GameData testData;

  @BeforeEach
  public void setUp() throws FileNotFoundException {
    initialStateParser = new LowestStateRandom();
    initialStateParser.setRandomSeed(7);
    DataController myDataController = new DataController();
    testData = myDataController.getGameConfigurationData(new File("data/config/2048.sim"));
  }

  @Test
  void generateInitialState2by2() {
    int expected00 = 0;
    int expected01 = 0;
    int expected10 = 1;
    int expected11 = 1;
    Grid startingGrid = initialStateParser.generateInitialState(2, 2, testData);
    assertEquals(expected00, startingGrid.getCell(0, 0).getCurrentState());
    assertEquals(expected01, startingGrid.getCell(0, 1).getCurrentState());
    assertEquals(expected10, startingGrid.getCell(1, 0).getCurrentState());
    assertEquals(expected11, startingGrid.getCell(1, 1).getCurrentState());
  }

  @Test
  void checkNumberOfNonZeroStates() {
    int expectedNonZero = 2;
    Grid startingGrid = initialStateParser.generateInitialState(2, 2, testData);
    int totalFound = 0;
    for (Cell c : startingGrid.getCellsInGrid()) {
      int curState = c.getCurrentState();
      if (curState != 0) {
        totalFound++;
      }
    }
    assertEquals(expectedNonZero, totalFound);
  }

  @Test
  void generateInitialStateCheckMaxAndMin() {
    int minExpected = 0;
    int maxExpected = 1;
    Grid startingGrid = initialStateParser.generateInitialState(10, 10, testData);
    int minFound = 1000;
    int maxFound = 0;
    for (Cell c : startingGrid.getCellsInGrid()) {
      int curState = c.getCurrentState();
      if (curState < minFound) {
        minFound = curState;
      }
      if (curState > maxFound) {
        maxFound = curState;
      }
    }
    assertEquals(maxExpected, maxFound);
    assertEquals(minExpected, minFound);

  }

}