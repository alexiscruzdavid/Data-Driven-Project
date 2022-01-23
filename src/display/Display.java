package ooga.display;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.DataController;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.resources.Config;
import ooga.util.AlertFactory;
import ooga.util.ExceptionHandlerFactory;
import ooga.util.NodeFactory;


public class Display implements Viewer {

    /**
     * button css IDs.
     */
    private static final String NEW_GAME_BUTTON_CSS = "NewGameButton";
    private static final String HOW_TO_PLAY_BUTTON_CSS = "HowToPlayButton";
    private static final String LEADERBOARD_BUTTON_CSS = "LeaderboardButton";
    private static final String CHEAT_CODE_BUTTON_CSS = "CheatCodeButton";
    private static final String LOAD_GAME_BUTTON_CSS = "LoadGameButton";
    private static final String SAVE_GAME_BUTTON_CSS = "SaveGameButton";

    /**
     * resource bundle file path.
     */
    private static final String RESOURCE_PACKAGE = "ooga.resources.";
    private static final String BUTTONS = "Buttons";

    /**
     * resource bundle key names.
     */
    private static final String TITLE_KEY = "Title";
    private static final String DISPLAY_WORD_SCORE_KEY = "DisplayWordScore";
    private static final String END_GAME_KEY = "EndGame";
    private static final String END_GAME_TEXT_KEY = "EndGameText";
    private static final String INSTRUCTIONS_PATH_KEY = "Instructions%s";
    private static final String SAVE_GAME_KEY = "SaveGame";
    private static final String SAVE_DESCRIPTION_KEY = "SaveDescription";
    private static final String STATE_NAME = "State";
    private static final String PIC_NAME = "Pic";
    private static final String ERROR_NAME = "Error";
    private static final String EXIT_NAME = "Exit";
    private static final String USERNAME_1 = "Username";
    private static final String USERNAME_2 = "username";
    private static final String HIGHEST_SCORE_EVER_1 = "Highest Score Ever";
    private static final String HIGHEST_SCORE_EVER_2 = "highestScoreEver";


    /**
     * cheat code text inputs
     */
    private static final String CHEAT_CODE_DEFAULT_INPUT = "";
    private static final String ENTER_CHEAT_CODE = "Enter a cheat code";

    /**
     * default style sheet file path.
     */
    private static final String DEFAULT_STYLESHEET =
            "/" + RESOURCE_PACKAGE.replace(".", "/") + "styles.css";

    /**
     * list for buttons.
     */
    private static final String[] BUTTON_NAMES = {"NewGame", "HowToPlay", "Leaderboard",
            "CheatCodes", "PlayButton", "Pause", "LoadGame", "SaveGame"};

    /**
     * list for button css IDs.
     */
    private static final String[] BUTTON_CSS_ID = {NEW_GAME_BUTTON_CSS, HOW_TO_PLAY_BUTTON_CSS,
            LEADERBOARD_BUTTON_CSS, CHEAT_CODE_BUTTON_CSS, LOAD_GAME_BUTTON_CSS, SAVE_GAME_BUTTON_CSS};

    /**
     * style class names.
     */
    private static final String OUTER_FRAME_STYLE_CLASS = "outer_frame";
    private static final String TOOLBAR_STYLE_CLASS = "toolbar";
    private static final String GRID_DISPLAY_STYLE_CLASS = "grid-display";
    private static final String SCORE_DISPLAY_STYLE_CLASS = "score_display";
    private static final String SCORE_STACK_STYLE_CSS = "score_stack";
    private static final String LABEL_STYLE_CLASS = "label";

    /**
     * frame and grid dimensions.
     */
    private static final int FRAME_WIDTH = Config.DEFAULT_FRAME_WIDTH;
    private static final int FRAME_HEIGHT = Config.DEFAULT_FRAME_HEIGHT;
    private static final int GRID_WIDTH = Config.DEFAULT_GRID_WIDTH;
    private static final int GRID_HEIGHT = Config.DEFAULT_GRID_HEIGHT;
    private static final int GRID_SCALAR_1 = 100;
    private static final int GRID_SCALAR_2 = 30;
    private static final int GRID_OFFSET = 5;
    private static final int GRID_EXPANSION = 400;
    private static final int GRID_MULTIPLIER = 8;
    private static final int GRID_ADDITION_VALUE = 25;

    /**
     * leaderboard dimensions.
     */
    private static final int LEADERBOARD_WIDTH = 300;
    private static final int LEADERBOARD_HEIGHT = 600;
    private static final int LEADERBOARD_SCALAR = 1000;

    /**
     * panes to set up HBoxes and VBoxes on the view.
     */
    private Pane gameTitlePane;
    private Pane gameSettingPane;
    private Pane gridStack;
    private Pane scoreStack;
    private Pane gameContentsPane;
    private Pane gridPane;
    private Pane outerFramePane;
    private Pane scoreDisplay;
    private TextInputDialog cheatCodeInput;

    /**
     * cheat code initialization values.
     */
    private String currentCheatCode = "";
    private boolean isCheatCodeUpdated = false;

    /**
     * setting up the game's display.
     */
    private Scene myScene;
    private Stage myStage;
    private String language;
    private Group gridToDisplay;
    protected ResourceBundle propertyResources;
    protected ResourceBundle buttonResources;
    private Map<String, Node> buttonMap;
    private Map<String, String> imgMap;
    private String gameTitle;
    private NodeFactory nodeFactory;
    private AlertFactory alertFactory;
    private Rectangle[][] rectArr;
    private int gridNumberOfRows;
    private int gridNumberOfColumns;
    private Label scoreLabel;
    private ExceptionHandlerFactory errorFactory;
    private Label timer;
    private TableView table = new TableView();
    private DataController dataController = new DataController();

    public Display(Stage myStage, Paint backgroundColor, String language) {
        this.myStage = myStage;
        this.language = language;
        cheatCodeInput = new TextInputDialog(CHEAT_CODE_DEFAULT_INPUT);
        cheatCodeInput.setHeaderText(ENTER_CHEAT_CODE);
        gridPane = new Pane();
        propertyResources = ResourceBundle.getBundle(
                RESOURCE_PACKAGE + language);
        buttonResources = ResourceBundle.getBundle(
                RESOURCE_PACKAGE + BUTTONS);
        nodeFactory = new NodeFactory(propertyResources, buttonResources);
        alertFactory = new AlertFactory(propertyResources);
        errorFactory = new ExceptionHandlerFactory(propertyResources);
        timer = nodeFactory.makeLabel("", Config.Game_FONT_SIZE, "TimerLabel");
        buttonMap = new HashMap<>();
        outerFramePane = createPaneObj(new VBox(), OUTER_FRAME_STYLE_CLASS);
        BackgroundFill fill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        outerFramePane.setBackground(background);
    }

    /**
     * set up the game's initial display
     * @return the scene of the initial game
     */
    @Override
    public Scene setupView() {
        myStage.setTitle(propertyResources.getString(TITLE_KEY));
        myStage.setHeight(FRAME_HEIGHT);
        myStage.setWidth(FRAME_WIDTH);
        gameTitlePane = createPaneObj(new HBox(), TOOLBAR_STYLE_CLASS);
        gameSettingPane = createPaneObj(new HBox(), TOOLBAR_STYLE_CLASS);
        gameContentsPane = createPaneObj(new HBox(), TOOLBAR_STYLE_CLASS);
        gridPane = createPaneObj(new HBox(), TOOLBAR_STYLE_CLASS);
        gameContentsPane.getChildren().addAll(gameSettingPane);
        outerFramePane.getChildren().addAll(gameTitlePane, gameContentsPane, gridPane);
        myScene = new Scene(outerFramePane, FRAME_WIDTH, FRAME_HEIGHT);
        myScene.getStylesheets().add(getClass().getResource(Config.DEFAULT_STYLESHEET).toExternalForm());
        return myScene;
    }

    public void bindTimer(StringProperty intp) {
        timer.textProperty().bind(intp);
    }

    /**
     * creates the display of the grid with the initial states
     * @param width        the width of the grid
     * @param height       the height of the grid
     * @param initialGrid  the initial states of the grid
     */
    @Override
    public void createDisplayGrid(int width, int height, Iterator<Integer> initialGrid) {
        gridNumberOfRows = width;
        gridNumberOfColumns = height;
        gridToDisplay = new Group();
        gridToDisplay.getStyleClass().add(GRID_DISPLAY_STYLE_CLASS);
        createRectArray(initialGrid);
        rectArr = new Rectangle[gridNumberOfRows][gridNumberOfColumns];
        double xFactor = 8 / (gridNumberOfRows + gridNumberOfColumns);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rectArr[j][i] = new Rectangle(
                        i * GRID_SCALAR_1 * GRID_MULTIPLIER / (gridNumberOfRows + gridNumberOfColumns) + GRID_SCALAR_2 * GRID_MULTIPLIER / (gridNumberOfRows
                                + gridNumberOfColumns),
                        j * GRID_SCALAR_1 * GRID_MULTIPLIER / (gridNumberOfRows + gridNumberOfColumns) + GRID_SCALAR_2 * GRID_MULTIPLIER / (gridNumberOfRows
                                + gridNumberOfColumns),
                        GRID_MULTIPLIER * (GRID_EXPANSION / GRID_WIDTH - GRID_OFFSET) / (gridNumberOfRows + gridNumberOfColumns),
                        GRID_MULTIPLIER * (GRID_EXPANSION / GRID_HEIGHT - GRID_OFFSET) / (gridNumberOfRows + gridNumberOfColumns));
                updateCell(j, i, initialGrid.next());
                gridToDisplay.getChildren().add(rectArr[j][i]);
            }
        }
        Rectangle background = new Rectangle(200, 400);
        background.setFill(Config.DEFAULT_GAME_INFO_BACKGROUND);
        Pane gameInfoStack = createPaneObj(new StackPane(), "score_stack");
        Pane gameInfoBox = createPaneObj(new VBox(), "game_info");
        gameInfoBox.getChildren().addAll(displayScore(0), timer);
        gameInfoStack.getChildren().addAll(background, gameInfoBox);
        gridPane.getChildren().addAll(gridToDisplay, gameInfoStack);
        updateView(initialGrid, 0);
        //createHowToPlayMenu();
        createTitleOfGame();
    }

    /**
     * initializes the display of the user's score for the particular game
     * @param score the player's score when playing the game
     * @return scoreStack, a stack pane to display the user's score
     */
    private Pane displayScore(int score) {
//        scoreStack = createPaneObj(new StackPane(), SCORE_STACK_STYLE_CSS);
        Rectangle background = new Rectangle(GRID_SCALAR_1, GRID_SCALAR_1);
        background.setFill(Color.LIGHTGREY);
        scoreDisplay = createPaneObj(new VBox(), SCORE_DISPLAY_STYLE_CLASS);
        Label lbl = nodeFactory.makeLabel(propertyResources.getString(DISPLAY_WORD_SCORE_KEY), Config.Game_FONT_SIZE, "ScoreDisplayID");
        styleLabel(lbl, DISPLAY_WORD_SCORE_KEY);
        scoreLabel = updateScoreLabel(score);
        scoreDisplay.getChildren().addAll(lbl, scoreLabel);
        //scoreStack.getChildren().addAll(background, scoreDisplay);
        return scoreDisplay;
    }

    /**
     * updates the score on the display
     * @param score the player's score when playing the game
     * @return scoreLabel, the player's current score
     */
    private Label updateScoreLabel(int score) {
        String scoreString = String.valueOf(score);
        scoreLabel = nodeFactory.makeLabel(scoreString, Config.Game_FONT_SIZE, "Score");
        styleLabel(scoreLabel, scoreString);
        return scoreLabel;
    }

    /**
     * mouse listener to be able to click on nodes
     * @param handler the event handler that will be triggered in response to a mouse event
     */
    @Override
    public void addMouseListenerToDisplayCells(EventHandler<MouseEvent> handler) {
        gridToDisplay.getChildren().forEach(node -> {
            node.setOnMouseClicked(handler);
        });
    }


    /**
     * updates the cells state with its new corresponding image
     * @param row the row of the cell
     * @param col the column of the cell
     * @param state the state of the cell
     */
    public void updateCell(int row, int col, int state) {
        String stateString = String.valueOf(state);
        if (state != 0) {
            Image img = null;
            try {
                img = new Image(new FileInputStream(imgMap.get(STATE_NAME + stateString + PIC_NAME)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            assert img != null;
            rectArr[row][col].setFill(new ImagePattern(img));
        } else {
            rectArr[row][col].setFill(Color.WHITE);
        }
    }

    /**
     * @return myScene
     */
    public Scene getScene() {
        return myScene;
    }


    /**
     * sets a style class to a label
     * @param lbl label name
     * @param id label ID
     * @return the label with the corresponding style class
     */
    private Label styleLabel(Label lbl, String id) {
        lbl.getStyleClass().add(LABEL_STYLE_CLASS);
        lbl.setId(id);
        return lbl;
    }

    /**
     * displays an end game alert when the user's game ends
     */
    public void displayEndGame() {
        alertFactory.makeAlertShow(propertyResources.getString(END_GAME_KEY),
                propertyResources.getString(END_GAME_TEXT_KEY), Alert.AlertType.INFORMATION);
    }


    /**
     * sets the configuration information, depending on the specific game being played
     * @param imgMap the images used for displaying objects in the view
     * @param gameTitle the user's current game title
     */
    @Override
    public void setConfigInfo(Map<String, String> imgMap, String gameTitle) {
        this.imgMap = imgMap;
        this.gameTitle = gameTitle;
    }

    /**
     * creates a menu displaying each game's instructions
     */
    public void createHowToPlayMenu() {
        alertFactory.makeAlertWait(gameTitle,
                propertyResources.getString(String.format(INSTRUCTIONS_PATH_KEY, gameTitle)), Alert.AlertType.INFORMATION);
    }

    /**
     * creates an alert to show that the user has correctly saved their current game state
     */
    public void displaySaveAlert() {
        alertFactory.makeAlertWait(propertyResources.getString(SAVE_GAME_KEY),
                propertyResources.getString(SAVE_DESCRIPTION_KEY), Alert.AlertType.INFORMATION);
    }

    /**
     * updates the view with the current score based on the user's game performance
     * @param gridIterator the iterator object that contains all the information about the states of
     *                     the cells
     * @param currentScore the current score of the game at the time of the update
     */
    @Override
    public void updateView(Iterator<Integer> gridIterator, int currentScore) {
        int count = 0;
        int rowCount = 0;
        while (gridIterator.hasNext()) {
            int row = rowCount % gridNumberOfRows;
            int col = count % gridNumberOfColumns;
            updateCell(row, col, gridIterator.next());
            count++;
            if (count % gridNumberOfColumns == 0) {
                rowCount++;
            }
        }

        scoreDisplay.getChildren().remove(scoreLabel);
        scoreDisplay.getChildren().add(updateScoreLabel(currentScore));
    }

    /**
     * creates game contents pane to be displayed on the view
     */
    public void createGameContents() {
        createGameSetting();
        gameContentsPane.getChildren().addAll(gameSettingPane);
    }

    /**
     * creates a new pane based on a given pane type and a style class
     * @param newObj a new pane object
     * @param styleClass type of style class
     * @return new pane with given pane type and style class
     */
    public Pane createPaneObj(Pane newObj, String styleClass) {
        newObj.getStyleClass().add(styleClass);
        return newObj;
    }


    /**
     * creates the rectangular array of cells for the game
     * @param gridIterator
     */
    private void createRectArray(Iterator<Integer> gridIterator) {
        rectArr = new Rectangle[gridNumberOfColumns][gridNumberOfRows];
        for (int i = 0; i < gridNumberOfRows; i++) {
            for (int j = 0; j < gridNumberOfColumns; j++) {
                rectArr[j][i] = new Rectangle(GRID_ADDITION_VALUE + i * GRID_EXPANSION / GRID_WIDTH + GRID_OFFSET, GRID_ADDITION_VALUE + j * GRID_EXPANSION / GRID_HEIGHT + GRID_OFFSET,
                        GRID_EXPANSION / GRID_WIDTH - GRID_OFFSET, GRID_EXPANSION / GRID_HEIGHT - GRID_OFFSET);
            }
        }
    }

    /**
     * displays an error alert
     * @param message the error message to be displayed
     */
    @Override
    public void displayError(String message) {
        errorFactory.handle(ERROR_NAME, message);
    }


    /**
     * adds a key listener so the user can press specific keys that relate to the game's functionality
     * @param handler the event to be triggered by the press of a key
     */
    @Override
    public void addKeyListener(EventHandler<KeyEvent> handler) {
        myScene.setOnKeyReleased(handler);
    }

    /**
     * creates each button and its corresponding functionality in the game's display
     * @param controller
     */
    @Override
    public void buttonPanel(Controller controller) {
        Button newGameButton = nodeFactory.makeButton(BUTTON_NAMES[0], controller);
        Button howToPlayButton = nodeFactory.makeButton(BUTTON_NAMES[1], this);
        Node cheatCodeButton = nodeFactory.makeButton(BUTTON_NAMES[3], this);
        Node playButton = nodeFactory.makeButton(BUTTON_NAMES[4], controller);
        Node pauseButton = nodeFactory.makeButton(BUTTON_NAMES[5], controller);
        Node leaderboardButton = nodeFactory.makeButton(BUTTON_NAMES[2], this);
        Node loadButton = nodeFactory.makeButton(BUTTON_NAMES[6], controller);
        Node saveButton = nodeFactory.makeButton(BUTTON_NAMES[7], controller);
        buttonMap.put(BUTTON_NAMES[0], newGameButton);
        buttonMap.put(BUTTON_NAMES[1], howToPlayButton);
        buttonMap.put(BUTTON_NAMES[2], leaderboardButton);
        buttonMap.put(BUTTON_NAMES[3], cheatCodeButton);
        buttonMap.put(BUTTON_NAMES[4], playButton);
        buttonMap.put(BUTTON_NAMES[5], pauseButton);
        buttonMap.put(BUTTON_NAMES[6], loadButton);
        buttonMap.put(BUTTON_NAMES[7], saveButton);
        createGameSetting();
    }

    /**
     * implements the cheat code menu's functionality
     */
    public void createCheatCodeMenu() {
        Optional<String> codeOptional = this.cheatCodeInput.showAndWait();
        try {
            currentCheatCode = codeOptional.get();
        } catch (NoSuchElementException nse) {
            return;
        }
        isCheatCodeUpdated = true;
    }

    /**
     * sets whether or not the cheat code is updated
     * @param newVal
     */
    public void setIsCheatCodeUpdated(boolean newVal) {
        isCheatCodeUpdated = newVal;
    }

    /**
     * determines whether or not the cheat code is updated
     * @return
     */
    public boolean isCheatCodeUpdated() {
        return isCheatCodeUpdated;
    }

    /**
     * creates the game setting pane on the display
     */
    private void createGameSetting() {
        buttonMap.forEach((s, node) -> gameSettingPane.getChildren().add(node));
    }

    /**
     * displays the title of the current game on the display
     */
    private void createTitleOfGame() {
        Label lbl = nodeFactory.makeLabel(gameTitle, Config.TITLE_FONT_SIZE, "GameTitle");
        styleLabel(lbl, gameTitle);
        gameTitlePane.getChildren().add(lbl);
    }

    /**
     * gets the current cheat code that the user inputs
     * @return the user's current cheat code
     */
    @Override
    public String getCurrentCheatCode() {
        return currentCheatCode;
    }

    /**
     * creates the leaderboard menu and displays the user's username and highest score ever
     */
    public void createLeaderboardMenu() {

        TableColumn<Profile, String> usernameCol = new TableColumn<>(USERNAME_1);
        TableColumn<Profile, String> highestScoreEver = new TableColumn<>(HIGHEST_SCORE_EVER_1);
        usernameCol.setPrefWidth(LEADERBOARD_WIDTH);
        highestScoreEver.setPrefWidth(LEADERBOARD_WIDTH);
        usernameCol.setCellValueFactory(new PropertyValueFactory<>(USERNAME_2));
        highestScoreEver.setCellValueFactory(new PropertyValueFactory<>(HIGHEST_SCORE_EVER_2));
        table.getItems().clear();
        table.getItems().addAll(dataController.getHighestScores());
        table.setPrefWidth(LEADERBOARD_HEIGHT);
        table.setPrefHeight(LEADERBOARD_WIDTH);
        table.getColumns().addAll(usernameCol, highestScoreEver);
        Popup popup = new Popup();
        popup.setWidth(LEADERBOARD_SCALAR);
        popup.setHeight(LEADERBOARD_SCALAR);
        popup.setX(LEADERBOARD_WIDTH);
        popup.setY(LEADERBOARD_WIDTH);
        popup.getContent().addAll(table);
        // create a button
        Button button = new Button(EXIT_NAME);
        // action event
        EventHandler<ActionEvent> event =
                e -> {
                    if (!popup.isShowing()) {
                        popup.show(myStage);
                    } else {
                        popup.hide();
                    }
                };

        // when button is pressed
        button.setOnAction(event);

        // add button
        popup.getContent().add(button);

        popup.show(myStage);


    }


}


