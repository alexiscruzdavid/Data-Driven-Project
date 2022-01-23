package ooga.gamelogic.initialstates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import ooga.controller.DataController;
import ooga.data.GameData;
import ooga.data.SIMFileReader;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FullGridTest {

  private Map<String, String> gameParams = new HashMap<>();
  private GameData testData;

  @BeforeEach
  public void setUp() throws FileNotFoundException {
    DataController myDataController = new DataController();
    Map<String, String> mapData = SIMFileReader.getMetadataFromFile(
        new File("data/config/tetris.sim"));
    mapData.put("NumberOfStates", "6");
    testData = new GameData(mapData);
  }

  @Test
  void generateInitialState2by2() {
    int expected00 = 5;
    int expected01 = 3;
    int expected10 = 4;
    int expected11 = 5;
    FullGrid initialStateParser = new FullGrid();
    initialStateParser.setRandomSeed(7);
    Grid startingGrid = initialStateParser.generateInitialState(2, 2, testData);
    assertEquals(expected00, startingGrid.getCell(0, 0).getCurrentState());
    assertEquals(expected01, startingGrid.getCell(0, 1).getCurrentState());
    assertEquals(expected10, startingGrid.getCell(1, 0).getCurrentState());
    assertEquals(expected11, startingGrid.getCell(1, 1).getCurrentState());
  }

  @Test
  void generateInitialStateMaxStates1() throws FileNotFoundException {
    Map<String, String> mapData = SIMFileReader.getMetadataFromFile(
        new File("data/config/Tetris.sim"));
    mapData.put("NumberOfStates", "1");
    testData = new GameData(mapData);
    int expected00 = 1;
    int expected01 = 1;
    int expected10 = 1;
    int expected11 = 1;
    FullGrid initialStateParser = new FullGrid();
    initialStateParser.setRandomSeed(7);
    Grid startingGrid = initialStateParser.generateInitialState(2, 2, testData);
    assertEquals(expected00, startingGrid.getCell(0, 0).getCurrentState());
    assertEquals(expected01, startingGrid.getCell(0, 1).getCurrentState());
    assertEquals(expected10, startingGrid.getCell(1, 0).getCurrentState());
    assertEquals(expected11, startingGrid.getCell(1, 1).getCurrentState());
  }

  @Test
  void generateInitialStateCheckMaxAndMin() {
    int minExpected = 1;
    int maxExpected = 6;
    FullGrid initialStateParser = new FullGrid();
    initialStateParser.setRandomSeed(7);
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