package ooga.gamelogic.spawners;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Cell;
import ooga.gamelogic.grid.Grid;

/**
 * Implements spawning a new entity by spawning a passed quantity of random cells in the grid.
 */
public class RandomCellSpawner implements Spawner {

  private static final int INCREMENT = 1;
  private int maxSpawnState;
  private int numberOfCellsToSpawn;
  private Random myRandom;

  /**
   * Constructor that sets the number of cells to spawn, the maximum spawn state to create, and
   * sets the random object to use when generating a spawn object on the grid.
   *
   * @param args the arguments passed containing information about how to spawn new agents
   */
  public RandomCellSpawner(int... args) {
    maxSpawnState = args[0];
    numberOfCellsToSpawn = args[1];
    myRandom = new Random();
  }

  /**
   * Spawns new agents in random empty spots on the grid.
   *
   * @param grid the grid we want to spawn the new entity on
   * @return the newly spawned agent
   */
  @Override
  public Agent spawnNewAgent(Grid grid) {
    Agent newSpawned = new Agent();
    List<Cell> cellsToUpdate = grid.getCellsInGrid();
    cellsToUpdate.removeIf(cell -> grid.isCellFull(cell.getCoordinates()));
    Collections.shuffle(cellsToUpdate);

    for (int i = 0; i < Math.min(numberOfCellsToSpawn, cellsToUpdate.size()); i++) {
      cellsToUpdate.get(i).setNextState(myRandom.nextInt(maxSpawnState) + INCREMENT);
      newSpawned.addCell(cellsToUpdate.get(i));
    }

    return newSpawned;
  }

  protected void setRandomSeed(int seed) {
    myRandom.setSeed(seed);
  }
}
