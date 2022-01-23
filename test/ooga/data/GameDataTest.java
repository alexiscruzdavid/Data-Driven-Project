package ooga.data;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDataTest {

  private Map<String, String> dataMap;

  @BeforeEach
  public void setUp() throws IOException {
    dataMap = SIMFileReader.getMetadataFromFile(new File("data/config/Tetris.sim"));

  }

  @Test
  void invalidGravityParameter() {
    dataMap.put("Gravity", "cool");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void noGravityParameter() {
    dataMap.remove("Gravity");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validGravityParameterTrue() {
    dataMap.put("Gravity", "True");
    GameData currentData = new GameData(dataMap);
    assertTrue(currentData.getGravity());
  }

  @Test
  void validGravityParameterFalse() {
    dataMap.put("Gravity", "False");
    GameData currentData = new GameData(dataMap);
    assertFalse(currentData.getGravity());
  }

  @Test
  void invalidStartingScoreParameter() {
    dataMap.put("StartingScore", "-5");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void noStartingScoreParameter() {
    dataMap.remove("StartingScore");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validStartingScoreParameter() {
    dataMap.put("StartingScore", "5");
    GameData currentData = new GameData(dataMap);
    assertEquals(5, currentData.getStartingScore());
  }

  @Test
  void validDestroyBlocksParameterFullRow() {
    dataMap.put("DestroyBlocks", "FullRow");
    GameData currentData = new GameData(dataMap);
    assertEquals("FullRow", currentData.getRemoveBlockConditions()[0]);
  }

  @Test
  void validDestroyBlocksParameterMultiple() {
    dataMap.put("DestroyBlocks", "FullRow,GroupDestroy");
    GameData currentData = new GameData(dataMap);
    assertArrayEquals(new String[]{"FullRow", "GroupDestroy"},
        currentData.getRemoveBlockConditions());
  }

  @Test
  void invalidDestroyBlocksParameter() {
    dataMap.put("DestroyBlocks", "FullRow, Thanksgiving");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonexistentDestroyBlocksParameter() {
    dataMap.remove("DestroyBlocks");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validIncreaseScoreInfo() {
    dataMap.put("IncreaseScore", "NumBlocksDestroyed");
    GameData currentData = new GameData(dataMap);
    assertEquals("NumBlocksDestroyed", currentData.getIncreaseScore());
  }

  @Test
  void invalidIncreaseScoreMultiple() {
    dataMap.put("IncreaseScore", "NumBlocksDestroyed, ScaledByState");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void invalidIncreaseScoreParameter() {
    dataMap.put("IncreaseScore", "FunTimes");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonexistentIncreaseScoreParameter() {
    dataMap.remove("IncreaseScore");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }


  @Test
  void validGridSizesParameter() {
    dataMap.put("GridSize", "10,10");
    GameData currentData = new GameData(dataMap);
    assertEquals(10, currentData.getNumberOfColumns());
    assertEquals(10, currentData.getNumberOfRows());
  }

  @Test
  void oneInvalidGridSizeParameter() {
    dataMap.put("GridSize", "-5, 3");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void twoInvalidGridSizeParameters() {
    dataMap.put("GridSize", "-5, -3");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void onlyOneGridSizeParameters() {
    dataMap.put("GridSize", "3");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonexistentGridSizeParameter() {
    dataMap.remove("GridSize");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonNumericalGridSizeParameter() {
    dataMap.put("GridSize", "great!");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validSpawnerParameters() {
    GameData currentData = new GameData(dataMap);
    assertEquals("FromTopSpawner", currentData.getSpawnLogic());
    assertEquals(4, currentData.getSpawnNumericalValues()[0]);
    assertEquals(7, currentData.getSpawnNumericalValues()[1]);
  }

  @Test
  void invalidSpawnerNumericalParameter() {
    dataMap.put("SpawnNewEntities", "True,RandomCellSpawner,-7,64");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void notNumSpawnerNumericalParameter() {
    dataMap.put("SpawnNewEntities", "True,RandomCellSpawner,yay,64");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void spawnerNoNumericalParameter() {
    dataMap.put("SpawnNewEntities", "True,RandomCellSpawner");
    GameData currentData = new GameData(dataMap);
    assertEquals("RandomCellSpawner", currentData.getSpawnLogic());
  }

  @Test
  void missingSpawnerParameter() {
    dataMap.remove("SpawnNewEntities");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void badSpawnerParameter() {
    dataMap.put("SpawnNewEntities", "True,Cat!,10,64");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }


  @Test
  void validActiveAgentCreatorInfo() {
    dataMap.put("SetActiveAgent", "AllCellsOnBoard");
    GameData currentData = new GameData(dataMap);
    assertEquals("AllCellsOnBoard", currentData.getActiveAgentCreator());
  }

  @Test
  void invalidActiveAgentCreatorMultiple() {
    dataMap.put("SetActiveAgent", "AllCellsOnBoard, RetainCurrentAgents");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void invalidActiveAgentCreatorParameter() {
    dataMap.put("SetActiveAgent", "DukeBasketball");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonexistentActiveAgentCreatorParameter() {
    dataMap.remove("SetActiveAgent");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validBoundariesParameterFullRow() {
    dataMap.put("MoveBoundaries", "OtherEntityBoundary");
    GameData currentData = new GameData(dataMap);
    assertEquals("OtherEntityBoundary", currentData.getBoundaryConditions()[0]);
  }

  @Test
  void validBoundariesParameterMultiple() {
    dataMap.put("MoveBoundaries", "EdgeBoundary,OtherEntityBoundary");
    GameData currentData = new GameData(dataMap);
    assertArrayEquals(new String[]{"EdgeBoundary", "OtherEntityBoundary"},
        currentData.getBoundaryConditions());
  }

  @Test
  void invalidBoundariesParameter() {
    dataMap.put("MoveBoundaries", "EdgeBoundary, Thanksgiving");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonexistentBoundariesParameter() {
    dataMap.remove("MoveBoundaries");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void invalidAnimationParameter() {
    dataMap.put("AnimationSpeed", "20");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void noAnimationParameter() {
    dataMap.remove("AnimationSpeed");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validAnimationParameter() {
    dataMap.put("AnimationSpeed", "1");
    GameData currentData = new GameData(dataMap);
    assertEquals(1, currentData.getAnimationSpeed());
  }

  @Test
  void invalidNumStatesParameter() {
    dataMap.put("NumberOfStates", "-20");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void noNumStatesParameter() {
    dataMap.remove("NumberOfStates");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validNumStatesParameter() {
    dataMap.put("NumberOfStates", "6");
    GameData currentData = new GameData(dataMap);
    assertEquals(6, currentData.getNumberOfStates());
  }

  @Test
  void validInitialStatesInfo() {
    dataMap.put("InitialStates", "FullGrid");
    GameData currentData = new GameData(dataMap);
    assertEquals("FullGrid", currentData.getInitialStates());
  }

  @Test
  void invalidInitialStatesMultiple() {
    dataMap.put("InitialStates", "NumBlocksDestroyed, ScaledByState");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void invalidInitialStatesParameter() {
    dataMap.put("InitialStates", "FunTimes");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonexistentInitialStatesParameter() {
    dataMap.remove("InitialStates");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validEndGameParameter() {
    dataMap.put("EndGame", "FullGrid");
    GameData currentData = new GameData(dataMap);
    assertEquals("FullGrid", currentData.getEndGame());
  }

  @Test
  void validEndGameInfo() {
    dataMap.put("EndGame", "Overflow");
    GameData currentData = new GameData(dataMap);
    assertEquals("Overflow", currentData.getEndGame());
  }

  @Test
  void invalidEndGameMultiple() {
    dataMap.put("EndGame", "NumBlocksDestroyed, ScaledByState");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void invalidEndGameParameter() {
    dataMap.put("EndGame", "FunTimes");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void nonexistentEndGameParameter() {
    dataMap.remove("EndGame");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validControlParameters() {
    GameData currentData = new GameData(dataMap);
    assertEquals("LeftOneCell", currentData.getControlRules().get("Left"));
  }

  @Test
  void invalidMoveParameter() {
    dataMap.put("Controls",
        "Left,SidewaysUntilBoundary/Right,RightUntilBoundary/Down,DownUntilBoundary/Up,UpUntilBoundary");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void invalidKeyParameter() {
    dataMap.put("Controls",
        "K,LeftUntilBoundary/Right,RightUntilBoundary/Down,DownUntilBoundary/Up,UpUntilBoundary");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void missingControlParameter() {
    dataMap.remove("Controls");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void badControlParameterFormatting() {
    dataMap.put("Controls",
        "Left,LeftUntilBoundary   Right,RightUntilBoundary/Down,DownUntilBoundary/Up,UpUntilBoundary");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }

  @Test
  void validStatePictures() {
    GameData currentData = new GameData(dataMap);
    assertEquals("data/Tetris/Tetris_Blue_Block.png",
        currentData.getStatePictures().get("State1Pic"));
  }

  @Test
  void notEnoughStatePictures() {
    dataMap.remove("State1Pic");
    assertThrows(InvalidParameterException.class, () -> new GameData(dataMap));
  }
}