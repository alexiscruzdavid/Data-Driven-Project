package ooga.gamelogic.spawners;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomCellSpawnerTest {

  private RandomCellSpawner spawner;
  private Grid grid;

  @BeforeEach
  void setup() {
    int[] args = new int[2];
    args[0] = 6;
    args[1] = 2;
    spawner = new RandomCellSpawner(args);
    grid = new Grid(4, 4);


  }

  @Test
  void spawnNewEntityCheckSpawnSize() {
    Agent newSpawn = spawner.spawnNewAgent(grid);
    grid.updateCells();
    assertEquals(2, newSpawn.getBody().size());
  }

  @Test
  void spawnNewEntityCheckSpawnValue() {
    int[] args = new int[2];
    args[0] = 6;
    args[1] = 1;
    spawner = new RandomCellSpawner(args);
    spawner.setRandomSeed(7);
    Agent newSpawn = spawner.spawnNewAgent(grid);
    grid.updateCells();
    assertEquals(5, newSpawn.getBody().get(0).getCurrentState());
  }

  @Test
  void spawnAmountAboveGridSize() {
    int[] args = new int[2];
    args[0] = 6;
    args[1] = 200;
    spawner = new RandomCellSpawner(args);
    spawner.setRandomSeed(7);
    Agent newSpawn = spawner.spawnNewAgent(grid);
    grid.updateCells();
    assertEquals(16, newSpawn.getBody().size());
  }

  @Test
  void spawnFullGrid() {
    int[] args = new int[2];
    args[0] = 6;
    args[1] = 20;
    spawner = new RandomCellSpawner(args);
    spawner.setRandomSeed(7);
    spawner.spawnNewAgent(grid);
    grid.updateCells();
    Agent newSpawn = spawner.spawnNewAgent(grid);
    grid.updateCells();
    assertEquals(0, newSpawn.getBody().size());
  }
}