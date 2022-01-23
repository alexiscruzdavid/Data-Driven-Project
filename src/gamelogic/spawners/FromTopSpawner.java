package ooga.gamelogic.spawners;

import java.util.Random;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;

/**
 * Implements spawning a new entity by spawning from the top of the grid.
 */
public class FromTopSpawner implements Spawner {

  private static final int NUM_GRID_DIVISIONS = 3;
  private static final int INCREMENT = 1;
  private static final int STARTING_ROW = 0;

  private int sizeOfEntity;
  private int numberOfStates;
  private Random myRandom;

  /**
   * Constructor that sets the size of the agent to generate, the number of states to create, and
   * sets the random object to use when generating a spawn agent from the top.
   *
   * @param args the arguments passed containing information about how to spawn new agents
   */
  public FromTopSpawner(int... args) {
    sizeOfEntity = args[0];
    numberOfStates = args[1];
    myRandom = new Random();
  }

  /**
   * Spawns a new agent from the top of the grid.
   *
   * @param grid the grid we want to spawn the new agent on
   * @return the newly spawned entity
   */
  @Override
  public Agent spawnNewAgent(Grid grid) {
    Agent newSpawned = new Agent();
    newSpawned.generateRandomBody(STARTING_ROW,
        (grid.getNumberOfColumns() / NUM_GRID_DIVISIONS + myRandom.nextInt(
            grid.getNumberOfColumns() / 3)),
        myRandom.nextInt(numberOfStates) + INCREMENT,
        Math.min(sizeOfEntity, grid.getCellsInGrid().size()));
    grid.addEntity(newSpawned);
    grid.updateCells();
    return newSpawned;
  }

  protected void setRandomSeed(int seed) {
    myRandom.setSeed(seed);
  }
}
