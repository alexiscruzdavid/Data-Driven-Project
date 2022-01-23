package ooga.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

import java.util.List;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.grid.ImmutableGrid;


/**
 * Interface for all the operations related to data within our program. Encapsulates all the
 * functionality behind the different data parsing, error checking, saving/loading files, etc., and
 * allows other parts of our program to count on all operations regarding data operating
 * successfully. Additionally, allows for full encapsulation of the particular implementation of how
 * the data is parsed. The rest of the program has no knowledge of the types of files used, etc.
 */
public interface Data {

  /**
   * Reads in a sim file and returns a GameData object that contains all the information needed to
   * configure the game.
   *
   * @param simFile the file passed with simulation information
   * @return GameData object set with all the information needed to configure the game
   * @throws FileNotFoundException     an exception indicating that the file passed was not a valid
   *                                   file
   * @throws InvalidParameterException an exception indicating that a parameter within the config
   *                                   file was invalid
   */
  GameData getGameConfigurationData(File simFile)
      throws FileNotFoundException, InvalidParameterException;

  /**
   * Method that saves the current state of the game, and the information about the game in order to
   * load it in and resume playing.
   *
   * @param currentGrid the current grid that we want to save the states of
   * @return a boolean indicating if the current grid has been saved
   * @throws IOException      if there is an error in saving the file
   * @throws RuntimeException if there is an error in
   */
  boolean saveGame(ImmutableGrid currentGrid) throws IOException;

  void savePlayerProfile(Profile player);

  List<Profile> getHighestScores();

  void setCurrentPlayersHighScore(Long highScore);

  Grid loadGame(String filePath) throws RuntimeException, IOException;

  Profile retrieveCurrentPlayer();

  Profile retrievePlayerProfile(String username);
}
