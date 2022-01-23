package ooga.controller;

import ooga.data.*;
import ooga.data.firebase.FireBase;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.gamelogic.grid.Grid;
import ooga.gamelogic.grid.ImmutableGrid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Data controller class implemented to manage loading data in, parsing data, saving game data and
 * getting the data into forms usable by the other classes in the games.
 */
public class DataController implements Data {

    private static final String HIGH_SCORE_ENTRY = "highscore%s";

    private GameData myCurrentGameData;
    private final FireBase fireBase;
    private ArrayList<Profile> highestScores = new ArrayList<>();

    private static String currentPlayer;

    /**
     * Creates the data controller, initializes the firebase upon creation, sets the highest scores in
     * the game based on the databases records.
     */
    public DataController() {
        fireBase = new FireBase();
        for (int i = 0; i < 10; i++) {
            highestScores.add(retrievePlayerProfile(String.format(HIGH_SCORE_ENTRY, i)));
        }
    }


    /**
     * Takes in the file location to game configuration data and gives back a GameData object with the
     * correct values to configure the game.
     *
     * @param simFile path to the game configuration data
     * @return a GameData object with all the game information stored within
     * @throws FileNotFoundException     indicates that an invalid file has been passed
     * @throws InvalidParameterException indicates that a parameter within the file is invalid
     */
    @Override
    public GameData getGameConfigurationData(File simFile)
            throws FileNotFoundException, InvalidParameterException {
        Map<String, String> gameDataMap = SIMFileReader.getMetadataFromFile(simFile);
        myCurrentGameData = new GameData(gameDataMap);
        return myCurrentGameData;
    }


    /**
     * Saves current grid states.
     */
    public boolean saveGame(ImmutableGrid currentGrid)
            throws IOException, InvalidParameterException {
        CsvFileReader.createCsv(currentGrid, myCurrentGameData.getGame());
        return true;
    }

    /**
     * Saves a player profile to the firebase database.
     *
     * @param player the player whose information we want to save
     */
    @Override
    public void savePlayerProfile(Profile player) {
        fireBase.update(player, player.getUsername());
    }

    /**
     * Loads a given file and returns a grid of the states passed.
     *
     * @param filePath the path to the file with the states we want to load in
     * @return a grid with the given states
     * @throws IOException indicating if the file or contents were invalid
     */
    public Grid loadGame(String filePath) throws IOException {
        return CsvFileReader.readFile(filePath);
    }

    /**
     * Retrieves a player profile from the database given the passed username.
     *
     * @param username the username of the player
     * @return the profile of the user whose username was specified
     */

    public Profile retrievePlayerProfile(String username) {
        if (username == null) {
            return new Profile();
        }
        HashMap<String, Object> playerData = (HashMap<String, Object>) fireBase.retrieve(username);
        return new Profile(playerData);
    }

    /**
     * Static method to set the current player for a given run of the game.
     *
     * @param username the username of the player who is the current player
     */

    public static void setCurrentPlayerInGame(String username) {
        currentPlayer = username;
    }

    /**
     * Retrieves the current player's profile from the database.
     *
     * @return the profile of the current player
     */
    public Profile retrieveCurrentPlayer() {
        return retrievePlayerProfile(currentPlayer);
    }

    /**
     * Sets the current player's highest score after a game has ended.
     *
     * @param highScore the score of the player
     */

    public void setCurrentPlayersHighScore(Long highScore) {
        Profile currentPlayer = retrieveCurrentPlayer();
        currentPlayer.setHighestScoreEver(highScore);
        List<Profile> highscores = getHighestScores();
        for (int i = 0; i < 10; i++) {
            if (currentPlayer.equals(highscores.get(i))) {
                highscores.add(i, currentPlayer);
                highscores.subList(i + 1, highscores.size())
                        .removeIf(profile -> profile.equals(currentPlayer));
                break;
            } else if (currentPlayer.getHighestScoreEver() > highscores.get(i).getHighestScoreEver()) {
                highscores.add(i, currentPlayer);
                break;
            }
        }

        highscores.sort(new HighestScoreOrganizer());
        for (int i = 0; i < highscores.size(); i++) {
            fireBase.update(highscores.get(i), String.format(HIGH_SCORE_ENTRY, i));
        }

        fireBase.update(currentPlayer, currentPlayer.getUsername());
    }

    /**
     * Gets the record scores of all users who have ever played the games.
     *
     * @return the list of profiles of users who have the highest scores
     */

    public List<Profile> getHighestScores() {
        highestScores.clear();
        for (int i = 9; i >= 0; i--) {
            highestScores.add(retrievePlayerProfile(String.format(HIGH_SCORE_ENTRY, i)));
        }
        highestScores.sort(new HighestScoreOrganizer());
        return highestScores;
    }

}



