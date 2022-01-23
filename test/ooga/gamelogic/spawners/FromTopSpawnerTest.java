package ooga.gamelogic.spawners;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FromTopSpawnerTest {

  private FromTopSpawner spawner;
  private Grid grid;

  @BeforeEach
  void setup() {
    int[] args = new int[2];
    args[0] = 6;
    args[1] = 2;
    spawner = new FromTopSpawner(args);
    grid = new Grid(5, 5);

  }

  @Test
  void spawnNewEntityCheckSpawnSize() {
    Agent newSpawn = spawner.spawnNewAgent(grid);
    grid.updateCells();
    assertEquals(6, newSpawn.getBody().size());
  }

  @Test
  void spawnNewEntityCheckSpawnValue() {
    int[] args = new int[2];
    args[0] = 6;
    args[1] = 1;
    spawner = new FromTopSpawner(args);
    spawner.setRandomSeed(7);
    Agent newSpawn = spawner.spawnNewAgent(grid);
    grid.updateCells();
    assertEquals(1, newSpawn.getBody().get(0).getCurrentState());
  }

  @Test
  void spawnAmountAboveGridSize() {
    int[] args = new int[2];
    args[0] = 200;
    args[1] = 6;
    spawner = new FromTopSpawner(args);
    spawner.setRandomSeed(7);
    Agent newSpawn = spawner.spawnNewAgent(grid);
    grid.updateCells();
    assertEquals(25, newSpawn.getBody().size());
  }

}