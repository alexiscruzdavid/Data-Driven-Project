package ooga.display;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.controller.DataController;
import ooga.data.firebase.playerprofiles.Profile;
import ooga.resources.Config;
import ooga.util.AlertFactory;
import ooga.util.NodeFactory;
import ooga.util.ExceptionHandlerFactory;

import java.util.ResourceBundle;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

public class ProfileEditorMenu {

    private final DataController dataController;

    /**
     * resource package file path
     */
    public static final String RESOURCE_PACKAGE = "ooga.resources.";

    /**
     * resource bundles
     */
    private final ResourceBundle resources;
    protected ResourceBundle buttonResources;

    /**
     * factories for buttons, alerts, and exceptions
     */
    private final NodeFactory nodeFactory;
    private final AlertFactory alertFactory;
    private final Stage stage;
    private javafx.scene.paint.Color myColor;
    private final Profile currentPlayer;
    private TextField changePasswordField;
    private ColorPicker changeTheme;
    private ComboBox<String> changeLang;
    private TextField usernameField;

    public ProfileEditorMenu(String language, Stage stage, Profile currentPlayer) {
        dataController = new DataController();
        resources = ResourceBundle.getBundle(RESOURCE_PACKAGE + language);
        buttonResources = ResourceBundle.getBundle(
                Config.BUTTON_RESOURCE_PACKAGE);
        nodeFactory = new NodeFactory(resources, buttonResources);
        alertFactory = new AlertFactory(resources);
        ExceptionHandlerFactory exceptionFactory = new ExceptionHandlerFactory(resources);
        this.stage = stage;
        this.currentPlayer = currentPlayer;
    }

    public Scene setupDisplay() {
        GridPane root = new GridPane();
        root.getStyleClass().add("menu-pane");
        root.setId("Menu");
        Label editProfileTitle = nodeFactory.makeLabel(resources.getString(Config.EDIT_PROFILE_TITLE), Config.TITLE_FONT_SIZE,
                Config.EDIT_PROFILE_TITLE_ID);
        Label usernameLabel = nodeFactory.makeLabel(resources.getString(Config.USERNAME_LABEL), Config.TEXT_FONT_SIZE,
                Config.EDIT_USERNAME_ID);
        usernameField = nodeFactory.makeTextField(Config.PROFILE_USERNAME_FIELD_ID);
        String username = "";
        if (!isNull(currentPlayer)) {
            username= currentPlayer.getUsername();
        }
        nodeFactory.setTextField(usernameField, false, username);
        Label passwordLabel = nodeFactory.makeLabel(resources.getString(Config.CHANGE_PASSWORD_LABEL), Config.TEXT_FONT_SIZE,
                Config.CHANGE_PASSWORD_LABEL_ID);
        changePasswordField = nodeFactory.makeTextField(Config.CHANGE_PASSWORD_FIELD_ID);
        String password = "";
        if (!isNull(currentPlayer)) {
            password = currentPlayer.getUsername();
        }
        nodeFactory.setTextField(changePasswordField, true, password);
        Label themeLabel = nodeFactory.makeLabel(resources.getString(Config.PREFFERED_THEME_LABEL), Config.TEXT_FONT_SIZE,
                Config.EDIT_THEME_LABEL);
        myColor = Config.DEFAULT_COLOR;
        Consumer<Color> response = color -> myColor = color;
        Color userColor = Config.DEFAULT_COLOR;
        changeTheme = nodeFactory.makeColorPicker(Config.EDIT_COLORPICKER, userColor,
                response);
        Label langLabel = nodeFactory.makeLabel(resources.getString(Config.SELECT_LANGUAGE_LABEL), Config.TEXT_FONT_SIZE, Config.EDIT_LANGUAGE_LABEL_ID);
        String[] langs = {Config.ENGLISH_STRING, Config.FRENCH_STRING, Config.SPANISH_STRING, Config.GERMAN_STRING};
        changeLang = nodeFactory.makeDropDown(Config.EDIT_LANGUAGE_COMBOBOX, langs, Config.EDIT_LANGUAGE_COMBOBOX_ID);
        Button saveChangesButton = nodeFactory.makeButton(Config.SAVE_CHANGES_BUTTON, this);
        root.add(editProfileTitle, 0, 0);
        root.add(usernameLabel, 0, 1);
        root.add(usernameField, 1, 1);
        root.add(passwordLabel, 0, 2);
        root.add(changePasswordField, 1, 2);
        root.add(themeLabel, 0, 3);
        root.add(changeTheme, 1, 3);
        root.add(langLabel, 0, 4);
        root.add(changeLang, 1, 4);
        root.add(saveChangesButton, 0, 5);
        return new Scene(root);
    }

    public void saveChanges() {
        if (changePasswordField.getText().isEmpty()) {
            alertFactory.makeAlertWait(resources.getString(Config.ERROR_ALERT), resources.getString(Config.MISSING_INFO),
                    Alert.AlertType.ERROR);
            return;
        } else {
            alertFactory.makeAlertWait(resources.getString(Config.SUCCESS_ALERT),
                    resources.getString(Config.CHANGES_SAVED_ALERT), Alert.AlertType.CONFIRMATION);
        }
        String username = usernameField.getText();
        String password = changePasswordField.getText();
        javafx.scene.paint.Color preferredTheme = changeTheme.getValue();
        String language = changeLang.getValue();
        dataController.savePlayerProfile(
                new Profile(username, password, 0L, preferredTheme.toString(), language));
        stage.close();
    }
}
