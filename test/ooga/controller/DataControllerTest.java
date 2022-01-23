package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import ooga.data.GameData;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.grid.ImmutableGrid;
import org.junit.jupiter.api.Test;

/**
 * Class to test the functionality of the DataController
 */
class DataControllerTest {

  @Test
  void getGameConfigurationDataGood() throws FileNotFoundException {
    DataController myDC = new DataController();
    GameData currentData = myDC.getGameConfigurationData(new File("data/config/tetris.sim"));
    assertTrue(currentData.getGravity());
    assertEquals("FullRow", currentData.getRemoveBlockConditions()[0]);
  }

  @Test
  void getGameConfigurationDataBadFile() {
    DataController myDC = new DataController();
    assertThrows(FileNotFoundException.class,
        () -> myDC.getGameConfigurationData(new File("data/config/chocolate.sim")));
  }

  @Test
  void getGameConfigurationDataBadParameters() {
    DataController myDC = new DataController();
    assertThrows(FileNotFoundException.class,
        () -> myDC.getGameConfigurationData(new File("data/config/error_invalid_parameters.sim")));
  }

  @Test
  void loadCSVValidFile() throws IOException {
    DataController myDC = new DataController();
    Grid loadedGrid = myDC.loadGame("data/saved/TEST_SAVED_FILE.csv");
    assertEquals(4, loadedGrid.getNumberOfColumns());
    assertEquals(4, loadedGrid.getNumberOfRows());
    assertEquals(0, loadedGrid.getCell(0, 0).getCurrentState());
    assertEquals(0, loadedGrid.getCell(1, 0).getCurrentState());
  }

  @Test
  void loadCSVInInvalidFile() {
    DataController myDC = new DataController();
    assertThrows(IOException.class, () -> myDC.loadGame("data/saved/cool.csv"));
  }

  @Test
  void playerProfileTest() {
    DataController myDC = new DataController();
    Profile test = new Profile("Test", "Test", 100L, "", "");
    myDC.savePlayerProfile(test);
    DataController.setCurrentPlayerInGame(test.getUsername());
    assertNotNull(myDC.retrieveCurrentPlayer());
    myDC.setCurrentPlayerInGame("");
  }

  @Test
  void saveCSV() throws IOException {
    DataController myDC = new DataController();
    ImmutableGrid loadedGrid = myDC.loadGame("data/saved/TEST_SAVED_FILE.csv");
    myDC.getGameConfigurationData(new File("data/config/2048.sim"));
    assertTrue(myDC.saveGame(loadedGrid));
  }

  @Test
  void retrieveCurrentPlayer() throws IOException {
    DataController myDC = new DataController();
    Profile testProfile = new Profile("tester", "tester", 10L, "0x0", "English");
    myDC.savePlayerProfile(testProfile);
    myDC.setCurrentPlayerInGame(testProfile.getUsername());
    assertEquals(testProfile, myDC.retrieveCurrentPlayer());
  }

  @Test
  void retrievePlayerFromUsername() {
    DataController myDC = new DataController();
    Profile testProfile = new Profile("testerNUM2", "tester", 10L, "0x0", "English");
    myDC.savePlayerProfile(testProfile);
    myDC.retrievePlayerProfile(testProfile.getUsername());
    assertEquals(testProfile, myDC.retrievePlayerProfile("testerNUM2"));
  }

  @Test
  void highScoresSetAndRetrieve() {
    DataController myDC = new DataController();
    Profile testProfile = new Profile("testerNUM2", "tester", 10L, "0x0", "English");
    myDC.savePlayerProfile(testProfile);
    myDC.setCurrentPlayerInGame(testProfile.getUsername());
    myDC.setCurrentPlayersHighScore(15L);
    myDC.retrievePlayerProfile(testProfile.getUsername());
    assertEquals(15L, myDC.retrievePlayerProfile("testerNUM2").getHighestScoreEver());
  }

}