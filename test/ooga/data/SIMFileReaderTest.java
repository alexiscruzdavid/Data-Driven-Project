package ooga.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SIMFileReaderTest {

  @Test
  void configurationWithSimTest() throws FileNotFoundException {
    Map<String, String> test = SIMFileReader.getMetadataFromFile(
        new File("data/config/Tetris.sim"));
    assertNotEquals(null, test);
    assertEquals("Tetris", test.get("Game"));
    assertEquals("True", test.get("Gravity"));
    assertEquals("0", test.get("StartingScore"));
    assertEquals("NewlySpawnedAgent", test.get("SetActiveAgent"));
  }
}