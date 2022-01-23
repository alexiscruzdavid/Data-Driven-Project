package ooga.display;

import static java.util.Objects.isNull;

import java.io.File;
import java.util.Arrays;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicInteger;


import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.DataController;
import ooga.controller.ProfileEditorController;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.resources.Config;
import ooga.util.AlertFactory;
import ooga.util.ExceptionHandlerFactory;
import ooga.util.NodeFactory;

public class GameSelectorMenu {

    /**
     * resource bundles
     */

    private final ResourceBundle resources;

    /**
     * factories for buttons, alerts, and exceptions
     */
    private final NodeFactory nodeFactory;
    private final AlertFactory alertFactory;
    private Controller controller;
    private ComboBox<String> selectGame;
    private final String language;
    private Profile currentPlayer;
    private final DataController dataController;
    private Pane root;

    public GameSelectorMenu(String language) {
        dataController = new DataController();
        resources = ResourceBundle.getBundle(Config.RESOURCE_PACKAGE + language);
        this.language = language;
        ResourceBundle buttonResources = ResourceBundle.getBundle(
                Config.BUTTON_RESOURCE_PACKAGE);
        nodeFactory = new NodeFactory(resources, buttonResources);
        alertFactory = new AlertFactory(resources);
        ExceptionHandlerFactory exceptionFactory = new ExceptionHandlerFactory(resources);
    }

    /**
     * displays pop up menu
     */
    public Scene setupDisplay() {
        root = new VBox();
        root.getStyleClass().add("game_select_pane");
        root.setId("Menu");
        Label welcomeLabel = nodeFactory.makeLabel(resources.getString(Config.WELCOME_LABEL), Config.TITLE_FONT_SIZE, Config.WELCOME_LABEL_ID);
        File f = new File(Config.DATA_CONFIG_PATH);
        String[] pathNames = f.list();
        String[] games = pathNames;
        AtomicInteger index = new AtomicInteger();
        Arrays.stream(pathNames).forEach(paths ->{
            games[index.get()] = paths.replace(".sim", "");
            index.getAndIncrement();
        });
        selectGame = nodeFactory.makeDropDown(Config.GAME_COMBOBOX, games, Config.GAME_COMBOBOX_ID);
        Button editProfileButton = nodeFactory.makeButton(Config.EDIT_PROFILE_BUTTON, this);
        Button goButton = nodeFactory.makeButton(Config.GO_BUTTON, this);
        Pane goAndEditProfile = buttonPanel(goButton, editProfileButton);
        root.getChildren().addAll(welcomeLabel, selectGame, goAndEditProfile);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(Config.DEFAULT_STYLESHEET)).toExternalForm());
        return scene;
    }

    /**
     * Edit Profile.
     */
    public void editProfile() {
        currentPlayer = dataController.retrieveCurrentPlayer();
        Stage editorStage = new Stage();
        ProfileEditorController controller = new ProfileEditorController(language, editorStage,
                currentPlayer);
        editorStage.setScene(controller.getProfileEditorMenu().setupDisplay());
        currentPlayer = dataController.retrieveCurrentPlayer();
    }

    /**
     * Checks to make sure all the values are selected in the pop-up window
     */
    public void go() {
        currentPlayer = dataController.retrieveCurrentPlayer();
        String myGame = selectGame.getValue();
        Color userColor = Config.DEFAULT_COLOR;
        if (!currentPlayer.getColor().equals("")) {
            userColor = Color.web(currentPlayer.getColor());
        }
        String language = "English";
        if (!currentPlayer.getLanguage().equals("")) {
            language = currentPlayer.getLanguage();
        }
        if (!isNull(selectGame.getValue())) {
            String color = currentPlayer.getColor();
            newGame(myGame,
                    language,
                    userColor);
            selectGame.setValue(null);
        } else
            alertFactory.makeAlertWait(resources.getString(Config.ERROR_ALERT), resources.getString(Config.SELECT_GAME), Alert.AlertType.ERROR);
    }

    /**
     * Displays Game Window.
     */

    public void newGame(String game, String lang, Paint background) {
        Stage newStage = new Stage();
        controller = new Controller(newStage, lang, game, background);
    }

    private Pane buttonPanel(Node... buttons) {
        HBox box = new HBox();
        for (Node b : buttons) {
            box.getChildren().add(b);
        }
        box.getStyleClass().add("toolbar");
        return box;
    }


    /**
     * getters and setters.
     */

    public Controller getController() {
        return controller;
    }


}
