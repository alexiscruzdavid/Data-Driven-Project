package ooga.gamelogic.spawners;

import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;

/**
 * Interface that defines the method for getting new agents to add to a game. Each class that
 * implements the interface applies a different way of spawning new agents for a game.
 */
public interface Spawner {

  /**
   * Method that is implemented differently in each class that implements the spawner interface.
   * That way, there are different ways that new agents can be spawned.
   *
   * @param grid the grid to spawn the new entity on
   * @return the new agent that has been spawned
   */
  Agent spawnNewAgent(Grid grid);
}
