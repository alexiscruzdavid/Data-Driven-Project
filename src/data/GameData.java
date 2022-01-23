package ooga.data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * GameData stores all the configuration data for the game in an encapsulated object and performs
 * error checking on the values passed in to ensure that they are valid.
 */

public class GameData {

    private static final int MAX_GRID_SIZE = 30;
    private static final int MIN_GRID_SIZE = 1;
    private static final int TWO_ITEM_OFFSET = 2;
    private static final int INDEX_ONE = 1;
    private static final int INDEX_ZERO = 0;
    private static final String INVALID_PARAMETER = "Invalid";
    private static final String REG_EX_TO_SPLIT_LIST = "\s*,";
    private static final String REG_EX_TO_SPLIT_CONTROLS = "/";
    private static final String PIC_STRING = "Pic";
    private static final int MAX_ANIMATION_SPEED = 5;
    private static final int MIN_ANIMATION_SPEED = 1;
    private static final String GRAVITY_KEY = "GravityKey";
    private static final String GAME_KEY = "Game";
    private static final String GRID_SIZE_KEY = "GridSizeKey";
    private static final String MOVES_KEY = "Moves";
    private static final String STARTING_SCORE_KEY = "StartingScoreKey";
    private static final String DESTROY_BLOCKS_KEY = "DestroyBlocksKey";
    private static final String INCREASE_SCORE_KEY = "IncreaseScoreKey";
    private static final String SET_ACTIVE_AGENT_KEY = "SetActiveAgentKey";
    private static final String MOUSE_CONTROL_KEY = "MouseControlKey";
    private static final String MOVE_BOUNDARIES_KEY = "MoveBoundariesKey";
    private static final String NUMBER_OF_STATES_KEY = "NumberOfStatesKey";
    private static final String SPAWN_ON_TURN_KEY = "SpawnOnTurnKey";
    private static final String SPAWNERS_KEY = "SpawnersKey";
    private static final String SPAWN_ON_HIT_FLOOR_KEY = "SpawnOnHitFloorKey";
    private static final String SPAWN_NEW_ENTITIES_KEY = "SpawnNewEntitiesKey";
    private static final String INITIAL_STATES_KEY = "InitialStatesKey";
    private static final String END_GAME_KEY = "EndGameKey";
    private static final String ANIMATION_SPEED_KEY = "AnimationSpeedKey";
    private static final String SHOW_TIMER_KEY = "ShowTimerKey";
    private static final String CONTROL_DIRECTIONS = "ControlDirections";
    private static final String CONTROL_DIRECTIONS_KEY = "ControlDirectionsKey";
    private static final String STATE_PICTURES_MESSAGE = "state pictures";
    private static final String SPAWNERS_MESSAGE = "spawners";
    private static final String CONTROL_MESSAGE = "controls";

    private final Map<String, String> passedProperties;
    private final String endGame;
    private final String increaseScore;
    private final String game;
    private Map<String, String> controlRules;
    private final Boolean gravity;
    private final Boolean spawnOnTurn;
    private final Boolean spawnOnHitFloor;
    private final Boolean mouseControl;
    private final String initialStates;
    private final int startingScore;
    private final String[] removeBlockConditions;
    private final String[] boundaryConditions;
    private int numberOfColumns;
    private int numberOfRows;
    private int[] spawnNumericalValues;
    private String spawnLogic;
    private final String activeAgentCreator;
    private Map<String, String> statePictures;
    private final int numberOfStates;
    private final int animationSpeed;
    private final boolean showTimer;
    private final ResourceBundle validParameters = ResourceBundle.getBundle(
            "ooga.resources.sim_valid_parameters");
    private final ResourceBundle configurationFileKeys = ResourceBundle.getBundle(
            "ooga.resources.configuration_file_keys");


    /**
     * Constructor for GameData object, for each instance variable (data), it verifies the passed
     * parameter and then sets it.
     *
     * @param gameProperties the map of properties passed with game config information
     * @throws InvalidParameterException exception thrown in case of bad value/information passed
     */
    public GameData(Map<String, String> gameProperties) throws InvalidParameterException {
        passedProperties = gameProperties;
        game = gameProperties.get(GAME_KEY);
        gravity = getPassedBoolean(configurationFileKeys.getString(GRAVITY_KEY));
        startingScore = getPassedNumber(configurationFileKeys.getString(STARTING_SCORE_KEY), 0,
                Double.POSITIVE_INFINITY);
        removeBlockConditions = getPassedStringArray(
                configurationFileKeys.getString(DESTROY_BLOCKS_KEY));
        increaseScore = getPassedString(configurationFileKeys.getString(INCREASE_SCORE_KEY));
        setGridSizes();
        setSpawnerInformation();
        setControlInformation();
        activeAgentCreator = getPassedString(configurationFileKeys.getString(SET_ACTIVE_AGENT_KEY));
        mouseControl = getPassedBoolean(configurationFileKeys.getString(MOUSE_CONTROL_KEY));
        boundaryConditions = getPassedStringArray(configurationFileKeys.getString(MOVE_BOUNDARIES_KEY));
        numberOfStates = getPassedNumber(configurationFileKeys.getString(NUMBER_OF_STATES_KEY), 0,
                Double.POSITIVE_INFINITY);
        spawnOnTurn = getPassedBoolean(configurationFileKeys.getString(SPAWN_ON_TURN_KEY));
        spawnOnHitFloor = getPassedBoolean(configurationFileKeys.getString(SPAWN_ON_HIT_FLOOR_KEY));
        initialStates = getPassedString(configurationFileKeys.getString(INITIAL_STATES_KEY));
        endGame = getPassedString(configurationFileKeys.getString(END_GAME_KEY));
        animationSpeed = getPassedNumber(configurationFileKeys.getString(ANIMATION_SPEED_KEY),
                MIN_ANIMATION_SPEED, MAX_ANIMATION_SPEED);
        showTimer = getPassedBoolean(configurationFileKeys.getString(SHOW_TIMER_KEY));
        setStatePicturePaths();
    }

    /**
     * Method to parse the information passed for a boolean, perform error checking for the boolean
     * string passed, and set the instance variable if possible.
     */
    private Boolean getPassedBoolean(String mapKey) throws InvalidParameterException {
        String passedValue = passedProperties.get(mapKey);
        verifyStringValue(mapKey, passedValue);
        return Boolean.parseBoolean(passedValue);
    }

    /**
     * Method to parse the information passed for how to destroy blocks, perform error checking for
     * the list passed, and set the instance variable if possible.
     */
    private String[] getPassedStringArray(String mapKey) {
        String[] passedValues = passedProperties.getOrDefault(mapKey, INVALID_PARAMETER)
                .split(REG_EX_TO_SPLIT_LIST);
        for (String currentVal : passedValues) {
            verifyStringValue(mapKey, currentVal);
        }
        return passedValues;
    }

    /**
     * Method to parse the information passed for a given String parameter, perform error checking for
     * the String passed.
     */
    private String getPassedString(String mapKey) {
        String passedValue = passedProperties.get(mapKey);
        verifyStringValue(mapKey, passedValue);
        return passedValue;
    }

    /**
     * Method to parse the information passed for a given String parameter, perform error checking for
     * the String passed.
     */
    private Integer getPassedNumber(String mapKey, double maxVal, double minVal) {
        String passedValue = passedProperties.get(mapKey);
        verifyNumericalVal(mapKey, passedValue, maxVal, minVal);
        return Integer.parseInt(passedValue);
    }


    /**
     * Method to parse the information passed for the grid size, perform error checking for the
     * numbers passed, and set the instance variables if possible.
     */
    private void setGridSizes() {
        String gridSizeKey = configurationFileKeys.getString(GRID_SIZE_KEY);
        String[] gridSizes = passedProperties.getOrDefault(gridSizeKey, INVALID_PARAMETER)
                .split(REG_EX_TO_SPLIT_LIST);
        for (String size : gridSizes) {
            verifyNumericalVal(gridSizeKey, size, MIN_GRID_SIZE, MAX_GRID_SIZE);

            try {
                numberOfColumns = Integer.parseInt(gridSizes[INDEX_ZERO]);
                numberOfRows = Integer.parseInt(gridSizes[INDEX_ONE]);
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidParameterException(gridSizeKey);
            }
        }
    }

    /**
     * Does error checking on the passed string value, ensures that it is contained in the valid
     * parameters properties file.
     *
     * @param propertyToCheck the property we want to check
     * @param valueToVerify   the value we want to error check
     * @throws InvalidParameterException exception indicating that an invalid parameter has been
     *                                   passed
     */
    private void verifyStringValue(String propertyToCheck, String valueToVerify) throws
            InvalidParameterException {
        if (valueToVerify == null || !validParameters.getString(propertyToCheck)
                .contains(valueToVerify)) {
            throw new InvalidParameterException(propertyToCheck);
        }
    }

    /**
     * Does error checking on the passed numerical value, ensures that is within the valid range for
     * the value, and is a numerical value.
     *
     * @param propertyToCheck the property we want to check
     * @param valueToVerify   the value we are checking for errors
     * @param minVal          the min value we want the passed value to take on
     * @param maxVal          the max value we want the passed value to take on
     * @throws InvalidParameterException exception indicating that an invalid parameter has been *
     *                                   passed
     */
    private void verifyNumericalVal(String propertyToCheck, String valueToVerify, double minVal,
                                    double maxVal) throws InvalidParameterException {
        try {
            int numericalVal = Integer.parseInt(valueToVerify);
            if (numericalVal < minVal || numericalVal > maxVal) {
                throw new InvalidParameterException();
            }
        } catch (Exception e) {
            throw new InvalidParameterException(propertyToCheck);
        }
    }

    /**
     * Takes in the list of spawner information, evaluates each parameter to determine if it is
     * valid.
     */
    private void setSpawnerInformation() {
        try {
            String[] spawnerInformation = passedProperties.get(
                    configurationFileKeys.getString(SPAWN_NEW_ENTITIES_KEY)).split(REG_EX_TO_SPLIT_LIST);
            verifyStringValue(configurationFileKeys.getString(SPAWNERS_KEY),
                    spawnerInformation[INDEX_ONE]);
            spawnLogic = spawnerInformation[INDEX_ONE];
            setSpawnerNumericalValues(spawnerInformation);
        } catch (Exception e) {
            throw new InvalidParameterException(SPAWNERS_MESSAGE);
        }
    }

    /**
     * Checks the spawner numerical values to see if they are valid, sets them in the array
     * appropriately.
     *
     * @param spawnerInformation the information passed about the spawner
     */
    private void setSpawnerNumericalValues(String[] spawnerInformation) {
        spawnNumericalValues = new int[spawnerInformation.length - TWO_ITEM_OFFSET];
        for (int i = 2; i < spawnerInformation.length; i++) {
            verifyNumericalVal(configurationFileKeys.getString(SPAWNERS_KEY), spawnerInformation[i], 0,
                    Double.POSITIVE_INFINITY);
            spawnNumericalValues[i - TWO_ITEM_OFFSET] = Integer.parseInt(spawnerInformation[i]);
        }
    }

    private void setStatePicturePaths() {
        statePictures = new HashMap<>();
        for (String key : passedProperties.keySet()) {
            if (key.contains(PIC_STRING)) {
                statePictures.put(key, passedProperties.get(key));
            }
        }
        if (statePictures.size() < numberOfStates) {
            throw new InvalidParameterException(STATE_PICTURES_MESSAGE);
        }
    }

    private void setControlInformation() {
        try {
            String[] passedControl = (passedProperties.get(
                            configurationFileKeys.getString(CONTROL_DIRECTIONS_KEY))
                    .split(REG_EX_TO_SPLIT_CONTROLS));
            List<String> control = new ArrayList<>(Arrays.asList(passedControl));
            controlRules = new HashMap<>();
            control.forEach(s -> {
                String[] keyAndMove = s.split(",");
                verifyStringValue(CONTROL_DIRECTIONS, keyAndMove[INDEX_ZERO]);
                verifyStringValue(MOVES_KEY, keyAndMove[INDEX_ONE]);
                controlRules.put(keyAndMove[0], keyAndMove[INDEX_ONE]);
            });
        } catch (Exception e) {
            throw new InvalidParameterException(CONTROL_MESSAGE);
        }
    }

    public int getStartingScore() {
        return startingScore;
    }

    public Boolean getGravity() {
        return gravity;
    }

    public Map<String, String> getControlRules() {
        return controlRules;
    }

    public String getInitialStates() {
        return initialStates;
    }

    public String[] getBoundaryConditions() {
        return boundaryConditions;
    }

    public String[] getRemoveBlockConditions() {
        return removeBlockConditions;
    }

    public String getIncreaseScore() {
        return increaseScore;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int[] getSpawnNumericalValues() {
        return spawnNumericalValues;
    }

    public String getSpawnLogic() {
        return spawnLogic;
    }

    public String getActiveAgentCreator() {
        return activeAgentCreator;
    }

    public Map<String, String> getStatePictures() {
        return statePictures;
    }

    public int getNumberOfStates() {
        return numberOfStates;
    }

    public String getEndGame() {
        return endGame;
    }

    public Boolean getSpawnOnHitFloor() {
        return spawnOnHitFloor;
    }

    public Boolean getSpawnOnTurn() {
        return spawnOnTurn;
    }

    public String getGame() {
        return game;
    }

    public boolean isShowTimer() {
        return showTimer;
    }

    public Boolean getMouseControl() {
        return mouseControl;
    }
}
