package ooga.engine;

import java.security.InvalidParameterException;

import ooga.data.GameData;
import ooga.exceptions.EndGameException;
import ooga.exceptions.ReflectionException;
import ooga.gamelogic.grid.Agent;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.grid.ImmutableGrid;

/**
 * Overview: Designed to encapsulate the model and allow the model being used to be substituted out
 * for any different model that implements these same methods. We see this as really a contract
 * between the controller and the model as to what functions the model will have and what it will
 * possibly be able to provide to the controller that can be used in the view. Regardless of which
 * game is being played, these methods are always able to be called and used, making our game
 * flexible with different game implementations.
 */
public interface Engine {

    /**
     * Does appropriate response in model to an event that has happened externally (key press).
     *
     * @param input the thing that has happened that needs a response
     * @return a boolean indicating if the grid has been updated
     */
    boolean respondToUserInputKeys(String input) throws ReflectionException;

    /**
     * Does appropriate response in model to an event that has happened externally (click).
     *
     * @param row    - row of input
     * @param column - column of input
     * @return a boolean indicating if the grid has been updated
     */
    boolean respondToUserInputMouse(int row, int column);


    /**
     * Advances one step in the game.
     *
     * @return boolean indicating if the next game state was achieved
     */
    boolean nextGameState(String newCheatCode)
            throws EndGameException, InvalidParameterException, ReflectionException;

    /**
     * Setup game from user passed parameters, assuming valid game parameters.
     *
     * @return boolean indicating if the game has been set up correctly
     */
    boolean setupGame(Grid myGrid, GameData metadata);

    /**
     * Resets the game to the initial starting state.
     *
     * @param newGrid the new grid to set the game states to
     * @return boolean indicating if the game has been reset
     */
    boolean resetGame(Grid newGrid);

    /**
     * Sets the difficulty of the particular game being played.
     *
     * @param difficultyLevel number corresponding to the level
     * @return boolean if the difficulty was set correctly
     * @deprecated Can be implemented in future games but not currently available in ours.
     */
    @Deprecated
    boolean adjustDifficulty(int difficultyLevel);

    /**
     * Get the current score of the game.
     *
     * @return the current game's score
     */
    int getGameScore();


    /**
     * Gets the current time in the game.
     *
     * @return the current time
     */
    int getGameTime();


    /**
     * @Deprecated Gets a hint that indicates a good cell to play.
     * @return the cell that would be good to play
     */
    Agent getCellHint();

    /**
     * Gets an immutable grid object, which contains immutable information about the grid's states and
     * width and height to be communicated to other parts of the program.
     *
     * @return immutable grid with information about models current state
     */
    ImmutableGrid getImmutableGrid();

    /**
     * Sets state of cell based off coordinates.
     *
     * @param row   cell row
     * @param col   cell column
     * @param state cell state
     */
    Boolean setState(int row, int col, int state);
}
