package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.data.GameData;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.display.Display;
import ooga.display.Viewer;
import ooga.exceptions.EndGameException;
import ooga.exceptions.ReflectionException;
import ooga.gamelogic.grid.Grid;


/**
 * Organizes and runs the different parts of the program, allowing the Display, GameEngine, and Data
 * to communicate with each other.
 */
public class Controller {

    private static final String PATH_TO_MODEL_ERRORS = "ooga.resources.model_errors_%s";
    private static final String PATH_TO_SIM_FILES = "data/config/%s.sim";
    private static final String CSV_EXTENSION = "*.csv";
    private static final String SAVED_FILE_PATH = "./data/saved/";
    private static final int GRID_WIDTH_HEIGHT = 400;
    private static final int GRID_OFFSET = 30;

    private final Stage myStage;
    private Viewer myDisplay;
    private Timeline myTimeline;
    private final LogicController myLogicController;
    private final DataController myDataController;
    private final String language;
    int framesPerSecond;
    private final ResourceBundle modelErrorsResources;

    /**
     * Creates a Controller to run a new instance of the program, using the passed scene to initialize
     * its display.
     *
     * @param myStage the stage on which the display elements should be added.
     */
    public Controller(Stage myStage, String language, String game, Paint background) {
        this.language = language;
        this.myStage = myStage;
        this.modelErrorsResources = ResourceBundle.getBundle(
                String.format(PATH_TO_MODEL_ERRORS, language));
        myTimeline = new Timeline();
        myLogicController = new LogicController();
        myDataController = new DataController();
        if (setupGame(new File(String.format(PATH_TO_SIM_FILES, game)), background)) {
            framesPerSecond = myLogicController.getGameData().getAnimationSpeed();
            initializeTimeline();
        }
    }


    /**
     * The "master" loop for setting up the game, triggers setup in view and in the game engine.
     *
     * @param file       the configuration file path from which to initialize the game
     * @param background the background with which we want to apply to the initial display
     * @return the new display
     */
    public boolean setupGame(File file, Paint background) {
        myDisplay = new Display(myStage, background, language);
        myStage.setScene(myDisplay.setupView());
        myDisplay.buttonPanel(this);
        myLogicController.setDisplay(myDisplay);
        if (!setupGameConfiguration(file)) {
            return false;
        }
        myLogicController.setupViewWithInformation();
        addMouseListenerToDisplay();
        addKeyListenerToDisplay();
        myStage.getScene().getStylesheets().add(String.format("ooga/resources/%s.css", "styles"));
        myStage.show();

        return true;
    }

    private boolean setupGameConfiguration(File file) {
        GameData configData;
        try {
            configData = myDataController.getGameConfigurationData(file);
            myLogicController.createGameEngine(configData);
        } catch (FileNotFoundException e) {
            handleErrors(modelErrorsResources.getString("invalid_file"));
            return false;
        } catch (InvalidParameterException e) {
            handleErrors(String.format(modelErrorsResources.getString("invalid_config"), e.getMessage()));
            return false;
        } catch (ReflectionException e) {
            handleErrors(modelErrorsResources.getString("reflection_error"));
            return false;
        }
        return true;
    }

    private void handleErrors(String message) {
        myTimeline.stop();
        myDisplay.displayError(message);
    }

    private void addKeyListenerToDisplay() {
        myDisplay.addKeyListener(event -> {
                    String input = event.getCode().toString().toLowerCase();
                    input = input.substring(0, 1).toUpperCase() + input.substring(1);
                    myLogicController.getGameEngine().respondToUserInputKeys(input);
                    myDisplay.updateView(myLogicController.getImmutableGrid().iterator(),
                            myLogicController.getCurrentScore());
                }
        );
    }

    /**
     * Returns the current logic controller of the game.
     *
     * @return myLogicController the logic controller currently in use
     */
    public LogicController getMyLogicController() {
        return myLogicController;
    }


    private void addMouseListenerToDisplay() {
        int width = myLogicController.getGameData().getNumberOfRows();
        int height = myLogicController.getGameData().getNumberOfColumns();

        myDisplay.addMouseListenerToDisplayCells(event -> {
            int gridX = (int) Math.round(width * (event.getX() - GRID_OFFSET) / GRID_WIDTH_HEIGHT);
            int gridY = (int) Math.round(height * (event.getY() - GRID_OFFSET) / GRID_WIDTH_HEIGHT);
            myLogicController.getGameEngine().respondToUserInputMouse(gridY, gridX);
        });
    }

    /**
     * Instructs the data controller to save the current player profile.
     *
     * @param player the player whose profile we want to save
     */
    public void saveProfile(Profile player) {
        myDataController.savePlayerProfile(player);
    }


    /**
     * Asks the data controller to save the current state of the game.
     *
     * @throws IOException exception to indicate that an error has occurred in saving the game
     */
    public void saveGame() throws IOException {
        myDataController.saveGame(myLogicController.getImmutableGrid());
        myDisplay.displaySaveAlert();
    }

    /**
     * Loads a game that the user chooses via a file chooser.
     *
     * @return a boolean indicating if the file has been loaded successfully
     * @throws IOException exception to indicate that an error has occurred in loading the game
     */
    public Boolean loadGame() throws IOException {
        FileChooser fileChooser = createFileBrowser();
        File file = fileChooser.showOpenDialog(myDisplay.getScene().getWindow());
        return readAndApplyFilePassed(file);
    }

    protected Boolean readAndApplyFilePassed(File file) throws IOException {
        Grid loadedGrid;
        if (file != null) {
            try {
                loadedGrid = myDataController.loadGame(file.getPath());
            } catch (InvalidParameterException e) {
                handleErrors(modelErrorsResources.getString("load_error"));
                return false;
            }
            myLogicController.applyNewGridValues(loadedGrid);
            return true;
        }
        return false;
    }

    //Creates FileBrowser so users can upload files
    private FileChooser createFileBrowser() {
        FileChooser fileChooser = new FileChooser();
        String currentPath = Paths.get(SAVED_FILE_PATH).toAbsolutePath().normalize().toString();
        String gamePath = myLogicController.getGameData().getGame();
        fileChooser.setInitialDirectory(new File(String.format(currentPath + "/%s/", gamePath)));
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("CSV Files", CSV_EXTENSION));
        return fileChooser;
    }


    public Viewer getDisplay() {
        return myDisplay;
    }


    /**
     * Creates timeline for our game, plays animation.
     */
    private void initializeTimeline() {
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(1.0 / framesPerSecond), e -> triggerGameLoop()));
        myTimeline.play();
    }

    private void triggerGameLoop() {
        try {
            myLogicController.step();
        } catch (EndGameException endGame) {
            checkAndRecordScore();
            myTimeline.stop();
            myDisplay.displayEndGame();
        } catch (InvalidParameterException exception) {
            handleErrors(modelErrorsResources.getString("invalid_cheat_code"));
        }
    }

    private void checkAndRecordScore() {
        Profile currentPlayer = myDataController.retrieveCurrentPlayer();
        if (currentPlayer.getHighestScoreEver() != null) {
            int currentScore = myLogicController.getCurrentScore();
            if (currentScore > currentPlayer.getHighestScoreEver()) {
                myDataController.setCurrentPlayersHighScore((long) currentScore);
            }
        }
    }



    /**
     * Pauses the timeline.
     *
     * @return a boolean indicating that the game has been paused
     */
    public boolean pauseGame() {
        myTimeline.pause();
        return true;
    }

    /**
     * Plays the timeline.
     *
     * @return a boolean indicating that the game has been resumed
     */
    public boolean playGame() {
        myTimeline.play();
        return true;
    }

    /**
     * Restarts the game in the backend via the logic controller, updates the view with the new
     * information, and resumes the timeline.
     *
     * @return a boolean indicating if the game has been restarted correctly
     */
    public boolean restartGame() {
        myLogicController.restartGame();
        myDisplay.updateView(myLogicController.getImmutableGrid().iterator(), 0);
        myTimeline.play();
        return true;
    }

}
